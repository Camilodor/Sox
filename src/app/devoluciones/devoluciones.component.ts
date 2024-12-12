import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';
@Component({
  selector: 'app-devoluciones',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent],
  templateUrl: './devoluciones.component.html',
  styleUrls: ['./devoluciones.component.css'],
})
export class DevolucionesComponent {
  devoluciones = [
    { id: 1, nombre: 'Carlos Ruiz', mercancia: 'Mercancía C', razon: 'Producto defectuoso' },
    { id: 2, nombre: 'Marta Díaz', mercancia: 'Mercancía D', razon: 'Error en el pedido' },
  
  ];
  devolucionSeleccionada: any = {};

  abrirFormularioEditar(devolucion: any) {
    this.devolucionSeleccionada = { ...devolucion }; 
  }

  crearDevolucion() {
    alert('Crear Devolución: Función aún no implementada.');
  }

  guardarCambios() {
    alert('Guardar Cambios: Función aún no implementada.');
  }

  eliminarDevolucion(id: number) {
    alert(`Devolución con ID: ${id} eliminada.`);
  }

  consultarDevolucion(id: number) {
    alert(`Consultar devolución con ID: ${id}.`);
  }
}