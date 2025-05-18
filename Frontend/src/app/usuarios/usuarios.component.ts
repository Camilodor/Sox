import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';
import api from '../services/api.services'; // ✅ Importa el servicio Axios
import { FormsModule } from '@angular/forms'; // ✅ Importa 

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent,FormsModule],
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css'],
})export class UsuariosComponent implements OnInit {
  usuarios: any[] = [];
  usuarioSeleccionado: any = {};

  // ✅ Aquí declaras el nuevoUsuario globalmente
  nuevoUsuario: any = {
    nombre_usuario: '',
    nombres: '',
    apellidos: '',
    tipo_documento_id: null,
    numero_documento: '',
    telefono: '',
    direccion: '',
    ciudad: '',
    email: '',
    contrasena: '',
    tiposrol_id: null
  };

  ngOnInit(): void {
    this.obtenerUsuarios();
  }

  async obtenerUsuarios() {
    try {
      const response = await api.get('/usuarios');
      this.usuarios = response.data;
    } catch (error) {
      console.error('Error al obtener usuarios:', error);
    }
  }

  abrirFormularioEditar(usuario: any) {
    this.usuarioSeleccionado = { ...usuario };
  }

  async crearUsuario() {
    try {
      const response = await api.post('/usuarios', this.nuevoUsuario);
      alert('✅ Usuario creado con éxito');
      this.obtenerUsuarios();
  
      this.nuevoUsuario = {
        nombre_usuario: '',
        nombres: '',
        apellidos: '',
        tipo_documento_id: '',
        numero_documento: '',
        telefono: '',
        direccion: '',
        ciudad: '',
        email: '',
        contrasena: '',
        tiposrol_id:''








      };
    } catch (error: any) {
      console.error('❌ Error al crear usuario:', error);
      if (error.response?.data) {
        alert('Error al crear usuario: ' + JSON.stringify(error.response.data));
      } else {
        alert('Error desconocido');
      }
    }
  }
  async guardarCambios() {
    try {
      const response = await api.put(`/usuarios/${this.usuarioSeleccionado.id}`, this.usuarioSeleccionado);
      alert('✅ Usuario actualizado con éxito');
      this.obtenerUsuarios();
    } catch (error: any) {
      console.error('❌ Error al actualizar usuario:', error);
      alert('Error al actualizar usuario: ' + JSON.stringify(error.response?.data || error));
    }
  }
  

  async eliminarUsuario(id: number) {
    try {
      await api.delete(`/usuarios/${id}`);
      alert(`Usuario eliminado`);
      this.obtenerUsuarios();
    } catch (error) {
      console.error('Error al eliminar usuario:', error);
    }
  }

  async consultarUsuario(id: number) {
    try {
      const response = await api.get(`/usuarios/${id}`);
      alert(JSON.stringify(response.data, null, 2));
    } catch (error) {
      console.error('Error al consultar usuario:', error);
    }
  }
}
