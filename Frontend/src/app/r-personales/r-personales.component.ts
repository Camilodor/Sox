import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';
import api from '../services/api.services';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-r-personales',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent, FormsModule],
  templateUrl: './r-personales.component.html',
  styleUrls: ['./r-personales.component.css']
})
export class RPersonalesComponent implements OnInit {
  personales: any[] = [];
  personaSeleccionada: any = {};
  nuevaPersona: any = {
    usuarios_id: null,
    nombre: '',
    apellido: '',
    parentezco: '',
    num_documento: '',
    tipo_documento_id: null,
    num_celular: '',
    num_direccion: ''
  };

  ngOnInit(): void {
    this.obtenerPersonales();
  }

  async obtenerPersonales() {
    try {
      const response = await api.get('/r_personales');
      this.personales = response.data;
    } catch (error) {
      console.error('Error al obtener personales:', error);
    }
  }

  abrirFormularioEditar(persona: any) {
    this.personaSeleccionada = { ...persona };
  }

  async crearPersona() {
    try {
      const response = await api.post('/r_personales', this.nuevaPersona);
      alert('✅ Registro creado con éxito');
      this.obtenerPersonales();
      this.nuevaPersona = {
        usuarios_id: "",
        nombre: '',
        apellido: '',
        parentezco: '',
        num_documento: '',
        tipo_documento_id: "",
        num_celular: '',
        num_direccion: ''
      };
    } catch (error: any) {
      console.error('❌ Error al crear persona:', error);
      if (error.response?.data) {
        alert('Error al crear persona: ' + JSON.stringify(error.response.data));
      } else {
        alert('Error desconocido');
      }
    }
  }
  async actualizarPersona() {
    try {
      const id = this.personaSeleccionada.id;
      const response = await api.put(`/r_personales/${id}`, this.personaSeleccionada);
      alert('✅ Persona actualizada con éxito');
      this.obtenerPersonales(); // recargar la lista
    } catch (error: any) {
      console.error('❌ Error al actualizar persona:', error);
      if (error.response?.data) {
        alert('Error al actualizar: ' + JSON.stringify(error.response.data));
      } else {
        alert('Error desconocido');
      }
    }
  }

  async eliminarPersona(id: number) {
    try {
      await api.delete(`/r_personales/${id}`);
      alert(`Registro eliminado`);
      this.obtenerPersonales();
    } catch (error) {
      console.error('Error al eliminar persona:', error);
    }
  }

  async consultarPersona(id: number) {
    try {
      const response = await api.get(`/r_personales/${id}`);
      alert(JSON.stringify(response.data, null, 2));
    } catch (error) {
      console.error('Error al consultar persona:', error);
    }
  }

  async guardarCambios() {
    alert('Función guardar aún no implementada.');
  }
}
