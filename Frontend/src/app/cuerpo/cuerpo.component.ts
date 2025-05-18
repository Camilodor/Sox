import { Component } from '@angular/core';
import { PaginaComponent } from "../pagina/pagina.component";
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-cuerpo',
  standalone: true,
  imports: [RouterOutlet,PaginaComponent, HeaderComponent, FooterComponent],
  templateUrl: './cuerpo.component.html',
  styleUrl: './cuerpo.component.css'
})
export class CuerpoComponent {



}
