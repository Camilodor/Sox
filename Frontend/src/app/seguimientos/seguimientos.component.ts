import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';

@Component({
  selector: 'app-seguimientos',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent],
  templateUrl: './seguimientos.component.html',
  styleUrls: ['./seguimientos.component.css']
})
export class SeguimientosComponent {
  seguimientos = [
    {
      id: 1,
      mercancias_id: 201,
      ubicacion: 'Bodega central',
      estado: 'En tránsito',
      fecha_hora: '2024-04-22 14:30',
      observaciones: 'Llegará en la tarde'
    }
  ];

  camposSeguimiento = [
    'mercancias_id',
    'ubicacion',
    'estado',
    'fecha_hora',
    'observaciones'
  ];

  seguimientoSeleccionado: any = {};

  abrirFormularioEditar(seguimiento: any) {
    this.seguimientoSeleccionado = { ...seguimiento };
  }

  eliminarSeguimiento(id: number) {
    alert(`Seguimiento con ID ${id} eliminado`);
  }

  consultarSeguimiento(id: number) {
    alert(`Consulta de seguimiento con ID ${id}`);
  }

  guardarCambios() {
    alert('Cambios guardados correctamente');
  }
}
