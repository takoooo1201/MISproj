import { Component } from '@angular/core';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true, // Make this component standalone
  imports: [HttpClientModule,FormsModule ],
  //styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  //username: string = '';
  //password: string = '';
  barCode: string='';
  responseData: { [key: string]: any } = {
    isExist: "Y",
  };
  constructor(private http: HttpClient) {}

  fetchInvoiceDetails() {
    const url = '/sales-api/BIZAPIVAN/biz';
    const body = new URLSearchParams();
    body.set('version', '1.0');
    body.set('action', 'bcv');
    body.set('barCode', 'Barcode');
    body.set('TxID', '0001');
    body.set('appId', 'EINV7202407292089');

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
  
  onSubmit(): void {
    if(this.responseData["isExist"]=="Y")
      alert('Login success');
    else
      alert('Login failed');
  }
}

