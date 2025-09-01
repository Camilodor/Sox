import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';
import { FormsModule } from '@angular/forms';
import api from '../services/api.services';

@Component({
  selector: 'app-r-laborales',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent, FormsModule],
  templateUrl: './r-laborales.component.html',
  styleUrls: ['./r-laborales.component.css']
})
export class RLaboralesComponent implements OnInit {
  laborales: any[] = [];
  laboralSeleccionado: any = {};
  nuevoLaboral: any = {
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
    this.obtenerLaborales();
  }

  async obtenerLaborales() {
    try {
      const response = await api.get('/r_laborales');
      this.laborales = response.data;
    } catch (error) {
      console.error('Error al obtener laborales:', error);
    }
  }

  abrirFormularioEditar(laboral: any) {
    this.laboralSeleccionado = { ...laboral };
  }

  async crearLaboral() {
    try {
      const response = await api.post('/r_laborales', this.nuevoLaboral);
      alert('✅ Registro creado con éxito');
      this.obtenerLaborales();
      this.nuevoLaboral = {
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
      console.error('❌ Error al crear laboral:', error);
      if (error.response?.data) {
        alert('Error al crear: ' + JSON.stringify(error.response.data));
      } else {
        alert('Error desconocido');
      }
    }
  }

  async actualizarLaboral() {
    try {
      const response = await api.put(`/r_laborales/${this.laboralSeleccionado.id}`, this.laboralSeleccionado);
      alert('✅ Registro actualizado con éxito');
      this.obtenerLaborales();
    } catch (error: any) {
      console.error('❌ Error al actualizar:', error);
      if (error.response?.data) {
        alert('Error al actualizar: ' + JSON.stringify(error.response.data));
      } else {
        alert('Error desconocido');
      }
    }
  }

  async eliminarLaboral(id: number) {
    try {
      await api.delete(`/r_laborales/${id}`);
      alert(`Registro eliminado`);
      this.obtenerLaborales();
    } catch (error) {
      console.error('Error al eliminar laboral:', error);
    }
  }

  async consultarLaboral(id: number) {
    try {
      const response = await api.get(`/r_laborales/${id}`);
      alert(JSON.stringify(response.data, null, 2));
    } catch (error) {
      console.error('Error al consultar laboral:', error);
    }
  }

  async guardarCambios() {
    alert('Función guardar aún no implementada.');
  }
}
