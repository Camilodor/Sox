import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Importa CommonModule
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';

@Component({
  selector: 'app-vehiculos',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent],
  templateUrl: './vehiculos.component.html',
  styleUrls: ['./vehiculos.component.css'],
})
export class VehiculosComponent {
  vehiculos = [
    { id: 1, placa: 'XYZ123', pesomaximo: '10 Toneladas', color: 'Rojo' },
    { id: 2, placa: 'ABC987', pesomaximo: '15 Toneladas', color: 'Azul' },
    // Más datos
  ];
  vehiculoSeleccionado: any = {};

  abrirFormularioEditar(vehiculo: any) {
    this.vehiculoSeleccionado = { ...vehiculo }; // Copiar el vehículo seleccionado
  }

  crearVehiculo() {
    alert('Crear Vehículo: Función aún no implementada.');
  }

  guardarCambios() {
    alert('Guardar Cambios: Función aún no implementada.');
  }

  eliminarVehiculo(id: number) {
    alert(`Vehículo con ID: ${id} eliminado.`);
  }

  consultarVehiculo(id: number) {
    alert(`Consultar vehículo con ID: ${id}.`);
  }
}