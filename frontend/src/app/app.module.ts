import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule } from '@angular/common/http'; // 引入 HttpClientModule
//import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { NotDevelopedComponent } from './not-developed/not-developed.component';
import { FormsModule } from '@angular/forms';
import { InvoiceDetailComponent } from './invoice-detail/invoice-detail.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { EinvoiceDetailComponent } from './einvoice-detail/einvoice-detail.component';

import { EinvoiceComponent } from './einvoice/einvoice.component';
import { EinvoiceLoginComponent } from './einvoice-login/einvoice-login.component';

@NgModule({
  declarations: [
    AppComponent,
    //EinvoiceLoginComponent,

    //EinvoiceComponent,
    //HomeComponent,
    //LoginComponent,
    //NotDevelopedComponent,
    //InvoiceDetailComponent,
    //EinvoiceDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule, // 註冊 HttpClientModule
    FormsModule,
    MatCardModule,
    MatTableModule
  ],

  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
