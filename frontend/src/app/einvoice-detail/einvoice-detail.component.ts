import { Component } from '@angular/core';
import { HttpClient, HttpHeaders, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common'; // Import CommonModule

@Component({
  selector: 'app-einvoice-detail',
  templateUrl: './einvoice-detail.component.html',
  styleUrls: ['./einvoice-detail.component.css'],
  standalone: true,
  imports: [ HttpClientModule,FormsModule,MatCardModule,CommonModule ],
})
export class EinvoiceDetailComponent {
  invNum: string = '';
  invDate: string = '';
  responseData: any;

  constructor(private http: HttpClient) {}

  fetchInvoiceDetails() {
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
    body.set('invNum', this.invNum);
    body.set('invDate', this.invDate);
    body.set('uuid', '0007');
    body.set('appID', 'EINV7202407292089');
    body.set('cardEncrypt', 'Tacopeko@7781');//your 載具密碼

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    this.http.post(url, body.toString(), { headers })
      .subscribe(response => {
        this.responseData = response;
      }, error => {
        console.error('Error fetching invoice details:', error);
      });
  }
}