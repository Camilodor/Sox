import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';

@Component({
  selector: 'app-tipos-pago',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent],
  templateUrl: './tipos-pago.component.html',
  styleUrls: ['./tipos-pago.component.css']
})
export class TiposPagoComponent {
  tiposPago = [
    { id: 1, nombre: 'Contado', descripcion: 'Pago inmediato en efectivo' },
    { id: 2, nombre: 'Transferencia', descripcion: 'Pago por transferencia bancaria' },
    { id: 3, nombre: 'Crédito', descripcion: 'Pago a plazo o fiado' },
    { id: 4, nombre: 'Tarjeta', descripcion: 'Pago con tarjeta débito o crédito' }
  ];

  pagoSeleccionado: any = {};

  abrirFormularioEditar(pago: any) {
    this.pagoSeleccionado = { ...pago };
  }

  eliminarPago(id: number) {
    alert(`Tipo de pago con ID ${id} eliminado`);
  }

  consultarPago(id: number) {
    alert(`Consulta de tipo de pago con ID ${id}`);
  }

  guardarCambios() {
    alert('Cambios guardados correctamente');
  }
}