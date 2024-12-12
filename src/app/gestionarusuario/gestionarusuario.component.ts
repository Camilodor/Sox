import { Component } from '@angular/core';
import { NavComponent } from '../nav/nav.component';
import { NavgestionarComponent } from '../navgestionar/navgestionar.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gestionarusuario',
  standalone: true,
  imports: [NavComponent,NavgestionarComponent,CommonModule],
  templateUrl: './gestionarusuario.component.html',
  styleUrl: './gestionarusuario.component.css'
})
export class GestionarusuarioComponent {


usuarioSeleccionado: any = {};

abrirFormularioEditar(usuario: any) {
  this.usuarioSeleccionado = { ...usuario }; 
}

crearUsuario() {
  alert('Crear Usuario: Función aún no implementada.');
}

guardarCambios() {
  alert('Guardar Cambios: Función aún no implementada.');
}

}