import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { NotDevelopedComponent } from './not-developed/not-developed.component';
import { InvoiceDetailComponent } from './invoice-detail/invoice-detail.component';
import { EinvoiceDetailComponent } from './einvoice-detail/einvoice-detail.component';
import { EinvoiceComponent } from './einvoice/einvoice.component';
import { EinvoiceLoginComponent } from './einvoice-login/einvoice-login.component';


//const routes: Routes = [];

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'not-developed', component: NotDevelopedComponent },
  { path: 'invoiceDetail', component: InvoiceDetailComponent },
  { path: 'einvoiceDetail', component: EinvoiceDetailComponent },
  { path: 'einvoice', component: EinvoiceComponent },
  { path: 'einvoiceLogin', component: EinvoiceLoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
