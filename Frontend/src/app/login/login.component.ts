import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  resumen = [
    { titulo: 'Mercancía', icono: 'fa fa-archive', total: 25, fondo: 'bg-verde' },
    { titulo: 'Despacho', icono: 'fa fa-truck', total: 12, fondo: 'bg-naranja' },
    { titulo: 'Entregas', icono: 'fa fa-check-circle', total: 8, fondo: 'bg-azul' },
    { titulo: 'Devoluciones', icono: 'fa fa-undo', total: 4, fondo: 'bg-rojo' }
  ];

  ngOnInit(): void {}
}
