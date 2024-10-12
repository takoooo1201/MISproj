import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  standalone: true, 
  imports: [RouterModule], 

  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  constructor(private router: Router) {}

  navigateToLogin() {
    this.router.navigate(['/einvoice']);
  }

  navigateToNotDeveloped() {
    this.router.navigate(['/not-developed']);
  }
}

