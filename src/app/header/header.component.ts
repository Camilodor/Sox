import { Component, Input } from '@angular/core';
import { NavgoComponent } from '../navgo/navgo.component';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NavgoComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  @Input() recibeTitulo: String = '';

}
