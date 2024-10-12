// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-root',
//   templateUrl: './app.component.html',
//   styleUrl: './app.component.css'
// })
// export class AppComponent {
//   title = 'frontend';
// }
import { Component, OnInit } from '@angular/core';
import { HelloService } from './hello.service';
import { RouterOutlet } from '@angular/router';
@Component({
  selector: 'app-root',
  // standalone: true,
  // imports: [RouterOutlet],
  template: `<router-outlet></router-outlet>`,//`<h1>{{ message }}</h1>`,//`<router-outlet></router-outlet>`,//`<h1>{{ message }}</h1>`
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  message: string="";

  constructor(private helloService: HelloService) {}

  ngOnInit() {
    this.helloService.getHello().subscribe(data => {
      this.message = data;
    });
  }
}
