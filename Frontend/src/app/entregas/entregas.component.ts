import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';

@Component({
  selector: 'app-entregas',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent],
  templateUrl: './entregas.component.html',
  styleUrls: ['./entregas.component.css']
})
export class EntregasComponent {
  entregas = [
    {
      id: 1,
      mercancias_id: 100,
      despacho_id: 55,
      usuario_id: 10,
      nombre_recibe: 'Carlos Sánchez',
      num_celular_recibe: '3104567890',
      observaciones: 'Entregado sin novedades',
      fecha_entrega: '2024-04-22',
      estado_entrega: 'Entregado'
    }
  ];

  camposEntrega: string[] = [
    'mercancias_id',
    'despacho_id',
    'usuario_id',
    'nombre_recibe',
    'num_celular_recibe',
    'observaciones',
    'fecha_entrega',
    'estado_entrega'
  ];

  entregaSeleccionada: any = {};

  abrirFormularioEditar(entrega: any) {
    this.entregaSeleccionada = { ...entrega };
  }

  eliminarEntrega(id: number) {
    alert(`Entrega con ID ${id} eliminada`);
  }

  consultarEntrega(id: number) {
    alert(`Consulta de entrega con ID ${id}`);
  }

  guardarCambios() {
    alert('Cambios guardados correctamente');
  }
}
