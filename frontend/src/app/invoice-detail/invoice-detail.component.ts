import { Component } from '@angular/core';

import { HttpClient, HttpHeaders, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common'; // Import CommonModule

@Component({
  selector: 'app-invoice-detail',
  templateUrl: './invoice-detail.component.html',
  styleUrls: ['./invoice-detail.component.css'],
  standalone: true,
  imports: [ HttpClientModule,FormsModule,MatCardModule,CommonModule ], 
})
export class InvoiceDetailComponent {
  invNum: string = '';
  invTerm: string = '';
  randomNumber: string ='';
  responseData: any;

  constructor(private http: HttpClient) {}

  fetchInvoiceDetails() {
    const url = '/invoice-api/PB2CAPIVAN/invapp/InvApp';
    const body = new URLSearchParams();
    body.set('version', '0.6');
    body.set('type', 'Barcode');
    body.set('invNum', this.invNum);
    body.set('action', 'qryInvDetail');
    body.set('generation', 'V2');
    body.set('invTerm', this.invTerm);
    body.set('UUID', '0001');
    body.set('randomNumber', this.randomNumber);
    body.set('appID', 'EINV7202407292089');
    body.set('isBuyerType', 'N');

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
