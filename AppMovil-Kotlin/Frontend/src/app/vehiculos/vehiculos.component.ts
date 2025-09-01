import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';

@Component({
  selector: 'app-vehiculos',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent],
  templateUrl: './vehiculos.component.html',
  styleUrls: ['./vehiculos.component.css']
})
export class VehiculosComponent {
  vehiculos = [
    {
      id: 1,
      num_placas: 'ABC123',
      nom_marca_vehiculo: 'Toyota',
      num_propietario_veh: '12345678',
      num_propietario_cel: '3111234567',
      direc_propietario: 'Calle 12 #45-67',
      ciudad_propietario: 'Bogotá',
      num_modelo_anio: '2022',
      color_vehiculo: 'Blanco',
      fecha_vencimiento_soat: '2025-01-01',
      fecha_vencimiento_tecno: '2025-06-01',
      nom_satelital: 'GPS Seguros',
      usuario_satelital: 'usuario123',
      contra_satelital: 'pass123',
      capacidad_carga: '2 toneladas'
    }
  ];

  camposVehiculo = [
    'num_placas',
    'nom_marca_vehiculo',
    'num_propietario_veh',
    'num_propietario_cel',
    'direc_propietario',
    'ciudad_propietario',
    'num_modelo_año',
    'color_vehiculo',
    'fecha_vencimiento_soat',
    'fecha_vencimiento_tecno',
    'nom_satelital',
    'usuario_satelital',
    'contra_satelital',
    'capacidad_carga'
  ];

  vehiculoSeleccionado: any = {};

  abrirFormularioEditar(vehiculo: any) {
    this.vehiculoSeleccionado = { ...vehiculo };
  }

  eliminarVehiculo(id: number) {
    alert(`Vehículo con ID ${id} eliminado`);
  }

  consultarVehiculo(id: number) {
    alert(`Consulta de vehículo con ID ${id}`);
  }

  guardarCambios() {
    alert('Cambios guardados correctamente');
  }
}