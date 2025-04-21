import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Importa CommonModule
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';

@Component({
  selector: 'app-entregas',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent],
  templateUrl: './entregas.component.html',
  styleUrls: ['./entregas.component.css'],
})
export class EntregasComponent {
  entregas = [
    { id: 1, nombre: 'Juan Pérez', mercancia: 'Mercancía A', destino: 'Ciudad X', observaciones: 'Entrega puntual' },
    { id: 2, nombre: 'Ana López', mercancia: 'Mercancía B', destino: 'Ciudad Y', observaciones: 'Revisar documentación' },
    // Más datos
  ];
  entregaSeleccionada: any = {};

  abrirFormularioEditar(entrega: any) {
    this.entregaSeleccionada = { ...entrega }; // Copiar la entrega seleccionada
  }

  crearEntrega() {
    alert('Crear Entrega: Función aún no implementada.');
  }

  guardarCambios() {
    alert('Guardar Cambios: Función aún no implementada.');
  }

  eliminarEntrega(id: number) {
    alert(`Entrega con ID: ${id} eliminada.`);
  }

  consultarEntrega(id: number) {
    alert(`Consultar entrega con ID: ${id}.`);
  }
}