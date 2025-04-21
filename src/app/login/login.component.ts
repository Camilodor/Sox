import { Component } from '@angular/core';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [NavComponent,VerticalNavComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

}
