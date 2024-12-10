import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';

@Component({
  selector: 'app-mercancia',
  standalone: true,
  imports: [CommonModule,NavComponent, VerticalNavComponent],
  templateUrl: './mercancia.component.html',
  styleUrl: './mercancia.component.css'
})
export class MercanciaComponent {

  mercancias = [
    { id: 1, remitente: 'Empresa A', destino: 'Ciudad X', peso: '10kg', unidades: 5 },
    { id: 2, remitente: 'Empresa B', destino: 'Ciudad Y', peso: '15kg', unidades: 3 },
    { id: 3, remitente: 'Empresa C', destino: 'Ciudad Z', peso: '7kg', unidades: 10 },
    { id: 4, remitente: 'Empresa D', destino: 'Ciudad W', peso: '20kg', unidades: 1 },
    { id: 5, remitente: 'Empresa E', destino: 'Ciudad V', peso: '5kg', unidades: 8 },
    { id: 6, remitente: 'Empresa F', destino: 'Ciudad U', peso: '12kg', unidades: 4 },
    { id: 7, remitente: 'Empresa G', destino: 'Ciudad T', peso: '18kg', unidades: 2 },
    { id: 8, remitente: 'Empresa H', destino: 'Ciudad S', peso: '30kg', unidades: 6 },
    { id: 9, remitente: 'Empresa I', destino: 'Ciudad R', peso: '25kg', unidades: 3 },
    { id: 10, remitente: 'Empresa J', destino: 'Ciudad Q', peso: '8kg', unidades: 9 }
  ];

  mercanciaSeleccionada: any;

  abrirFormularioEditar(mercancia: any) {
    this.mercanciaSeleccionada = mercancia;
  }

  eliminarMercancia(id: number) {
    alert(`Mercancía con ID: ${id} eliminada`);
    
  }

  consultarMercancia(id: number) {
    alert(`Consultar mercancía con ID: ${id}`);
    
  }

  guardarCambios() {
    alert('Cambios guardados correctamente');
   
  }
}