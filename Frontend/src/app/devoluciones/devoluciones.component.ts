import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';

@Component({
  selector: 'app-devoluciones',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent],
  templateUrl: './devoluciones.component.html',
  styleUrls: ['./devoluciones.component.css']
})
export class DevolucionesComponent {
  devoluciones = [
    {
      id: 1,
      mercancias_id: 1001,
      usuarios_id: 2,
      proveedores_id: 3,
      fecha_devolucion: '2024-04-22',
      motivo_devolucion: 'Producto en mal estado',
      estado_devolucion: 'Pendiente',
      observaciones: 'Requiere verificación visual'
    }
  ];

  camposDevolucion = [
    'mercancias_id',
    'usuarios_id',
    'proveedores_id',
    'fecha_devolucion',
    'motivo_devolucion',
    'estado_devolucion',
    'observaciones'
  ];

  devolucionSeleccionada: any = {};

  abrirFormularioEditar(devolucion: any) {
    this.devolucionSeleccionada = { ...devolucion };
  }

  eliminarDevolucion(id: number) {
    alert(`Devolución con ID ${id} eliminada`);
  }

  consultarDevolucion(id: number) {
    alert(`Consulta de devolución con ID ${id}`);
  }

  guardarCambios() {
    alert('Cambios guardados correctamente');
  }
}
