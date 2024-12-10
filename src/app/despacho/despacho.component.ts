import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Importa CommonModule
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';

@Component({
  selector: 'app-despacho',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent],
  templateUrl: './despacho.component.html',
  styleUrls: ['./despacho.component.css'],
})
export class DespachoComponent {
  despachos = [
    { id: 1, fecha: '2024-12-10', mercancia: 'Mercancía A', vehiculo: 'Vehículo X' },
    { id: 2, fecha: '2024-12-11', mercancia: 'Mercancía B', vehiculo: 'Vehículo Y' },
    // Más datos
  ];
  despachoSeleccionado: any = {};

  abrirFormularioEditar(despacho: any) {
    this.despachoSeleccionado = { ...despacho }; // Copiar el despacho seleccionado
  }

  crearDespacho() {
    alert('Crear Despacho: Función aún no implementada.');
  }

  guardarCambios() {
    alert('Guardar Cambios: Función aún no implementada.');
  }

  eliminarDespacho(id: number) {
    alert(`Despacho con ID: ${id} eliminado.`);
  }

  consultarDespacho(id: number) {
    alert(`Consultar despacho con ID: ${id}.`);
  }
}
