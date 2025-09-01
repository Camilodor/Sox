import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';

@Component({
  selector: 'app-tipo-rol',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent],
  templateUrl: './tipos-rol.component.html',
  styleUrls: ['./tipos-rol.component.css']
})
export class TipoRolComponent {
  tiposRol = [
    { id: 1, nombre: 'Administrador', descripcion: 'Acceso completo al sistema' },
    { id: 2, nombre: 'Bodeguero', descripcion: 'Gestión de inventario y mercancías' },
    { id: 3, nombre: 'Conductor', descripcion: 'Entrega de productos y despacho' }
  ];

  rolSeleccionado: any = {};

  abrirFormularioEditar(rol: any) {
    this.rolSeleccionado = { ...rol };
  }

  eliminarRol(id: number) {
    alert(`Rol con ID ${id} eliminado`);
  }

  consultarRol(id: number) {
    alert(`Consulta de rol con ID ${id}`);
  }

  guardarCambios() {
    alert('Cambios guardados correctamente');
  }
}
