import { Component } from '@angular/core';
import { HttpClient, HttpHeaders, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common'; // Import CommonModule
import { tap } from 'rxjs/operators';
import { forkJoin } from 'rxjs';
import { MatListModule } from '@angular/material/list';
@Component({
  selector: 'app-einvoice',
  templateUrl: './einvoice.component.html',
  styleUrl: './einvoice.component.css',
  standalone: true,
  imports: [ HttpClientModule,FormsModule,MatCardModule,CommonModule, MatListModule ],
})


export class EinvoiceComponent {
  startDate: string = '';
  endDate: string = '';
  responseData: any;
  responseItem: any;

  constructor(private http: HttpClient) {}
  
  fetchInvoiceDetails() {
    const url = '/invoice-api/PB2CAPIVAN/invServ/InvServ';
    const currentTimestamp = Math.floor(Date.now() / 1000);
    const timeStamp = currentTimestamp + 10;

    const body = new URLSearchParams();
    body.set('version', '0.6');
    body.set('cardType', '3J0002');
    body.set('cardNo', '/YM7CBKZ');//your 條碼
    body.set('expTimeStamp', '2147483647');
    body.set('action', 'carrierInvChk');
    body.set('timeStamp', timeStamp.toString());
    body.set('startDate', this.startDate);
    body.set('endDate', this.endDate);
    body.set('onlyWinningInv', 'N');
    body.set('uuid', '0007');
    body.set('appID', 'EINV7202407292089');
    body.set('cardEncrypt', 'Tacopeko@7781');//your 載具密碼

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    this.http.post(url, body.toString(), { headers })
      .subscribe(response => {
        this.responseData = response;
        console.log(this.responseData.details[0]['invDate']["year"]+1911);
        // for(let i=0;i<this.responseData.details.length;i++){
        //   this.responseData.details[i]["items"]=["a","b","c"];
        // }
        for (let i = 0; i < this.responseData.details.length; i++) {
          const year = this.responseData.details[i]['invDate']["year"]+1911;
          const month = this.responseData.details[i]['invDate']["month"];
          const date = this.responseData.details[i]['invDate']["date"];
          const invNum = this.responseData.details[i]['invNum'];
        
          // Formatting the month and date to always have two digits
          const formattedMonth = month < 10 ? '0' + month : month;
          const formattedDate = date < 10 ? '0' + date : date;
        
          // Combining year, month, and date into yyyymmdd format
          const formattedDateString = `${year}/${formattedMonth}/${formattedDate}`;
          console.log(`Invoice Number: ${invNum}`);
          console.log(`Formatted Date: ${formattedDateString}`);
          // Now you can use `formattedDateString` as needed
          const url = '/invoice-api/PB2CAPIVAN/invServ/InvServ';
          const currentTimestamp = Math.floor(Date.now() / 1000);
          const timeStamp = currentTimestamp + 10;

          const body = new URLSearchParams();
          body.set('version', '0.5');
          body.set('cardType', '3J0002');
          body.set('cardNo', '/YM7CBKZ');//your 條碼
          body.set('expTimeStamp', '2147483647');
          body.set('action', 'carrierInvDetail');
          body.set('timeStamp', timeStamp.toString());
          body.set('invNum', invNum);
          body.set('invDate', formattedDateString);
          body.set('uuid', '0007');
          body.set('appID', 'EINV7202407292089');
          body.set('cardEncrypt', 'Tacopeko@7781');//your 載具密碼

          const headers = new HttpHeaders({
            'Content-Type': 'application/x-www-form-urlencoded'
          });

          this.http.post(url, body.toString(), { headers })
            .subscribe(response => {
              this.responseItem = response;
              this.responseData.details[i]["items"]=this.filterInvoiceDetails(this.responseItem.details);
            }, error => {
              console.error('Error fetching invoice items details:', error);
            });
        }

      }, error => {
        console.error('Error fetching invoice details:', error);
      });
  }
  filterInvoiceDetails(details: any[]): any[] {
    return details.filter(detail => {
      const unitPrice = parseFloat(detail.unitPrice);
      const amount = parseFloat(detail.amount);
      return unitPrice > 0 && amount >= 0;
    });
  }

  
}
