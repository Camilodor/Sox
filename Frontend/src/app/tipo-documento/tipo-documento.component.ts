import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';

@Component({
  selector: 'app-tipo-documento',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent],
  templateUrl: './tipo-documento.component.html',
  styleUrls: ['./tipo-documento.component.css']
})
export class TipoDocumentoComponent {
  tiposDocumento = [
    { id: 1, nombre: 'Cédula de Ciudadanía', descripcion: 'Documento para ciudadanos colombianos' },
    { id: 2, nombre: 'Tarjeta de Identidad', descripcion: 'Menores de edad' },
    { id: 3, nombre: 'Cédula de Extranjería', descripcion: 'Documento para extranjeros' },
    { id: 4, nombre: 'Pasaporte', descripcion: 'Documento internacional' },
    { id: 5, nombre: 'NIT', descripcion: 'Número de identificación tributaria' }
  ];

  tipoSeleccionado: any = {};

  abrirFormularioEditar(tipo: any) {
    this.tipoSeleccionado = { ...tipo };
  }

  eliminarTipo(id: number) {
    alert(`Tipo documento con ID ${id} eliminado`);
  }

  consultarTipo(id: number) {
    alert(`Consulta tipo documento con ID ${id}`);
  }

  guardarCambios() {
    alert('Cambios guardados correctamente');
  }
}