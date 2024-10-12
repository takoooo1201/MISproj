import { Component } from '@angular/core';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
// import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-einvoice-login',
  templateUrl: './einvoice-login.component.html',
  styleUrl: './einvoice-login.component.css',
  standalone: true,
  imports: [CommonModule,HttpClientModule,FormsModule,MatCardModule,RouterModule],
})
export class EinvoiceLoginComponent {
  username: string = '';
  password: string = '';
  message: string = '';
  signature: string = '';
  timeStamp: string = '';
  responseData: any;
  flag: boolean = false;


  constructor(private http: HttpClient,private router: Router) {}
  // constructor(private router: Router) {}

  navigateToHome() {
    this.router.navigate(['/home']);
  }

  onSubmit() {
    const loginData = {
      username: this.username,
      password: this.password
    };

    this.http.post<any>('/local-api/api/login', loginData)
    .subscribe(
      (response) => {
        console.log('Response:', response);
        this.message = response.message;
        console.log(this.message);
        this.signature = response.signature ;
        this.timeStamp = response.timeStamp ;
        const url = '/invoice-api/PB2CAPIVAN/Carrier/Aggregate';

        const body = new URLSearchParams();
        body.set('version', '1.0');
        body.set('serial', '0000000003');
        body.set('action', 'qryCarrierAgg');
        body.set('cardType', '3J0002');
        body.set('cardNo', this.username);//your 條碼
        body.set('cardEncrypt', this.password);//your 載具密碼
    
        body.set('timeStamp', this.timeStamp);
        console.log(this.timeStamp);
    
        body.set('uuid', '0004');
        body.set('appID', 'EINV7202407292089');
        body.set('signature', this.signature);
        
    
        const headers = new HttpHeaders({
          'Content-Type': 'application/x-www-form-urlencoded'
        });
    
        this.http.post(url, body.toString(), { headers })
          .subscribe(response => {
            this.responseData = response;
            // console.log(this.responseData.msg);
            if(this.responseData.msg==="執行成功"){
              this.flag=true;
            }
            
          }, error => {
            console.error('Error fetching invoice details:', error);
          });
      },
      (error) => {
        console.error('Login error', error);
      }
    );
    


    
  }
}
