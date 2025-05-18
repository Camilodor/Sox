import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';
import api from '../services/api.services';

@Component({
  selector: 'app-despachos',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    NavComponent,
    VerticalNavComponent
  ],
  templateUrl: './despachos.component.html',
  styleUrls: ['./despachos.component.css']
})
export class DespachosComponent implements OnInit {
  // Listado de despachos
  despachos: any[] = [];

  // Modelo para el formulario de creación
  nuevaDespacho: any = {
    mercancias_id: null,
    vehiculos_id: null,
    usuarios_id: null,
    tipo_pago_id: null,
    fecha_despacho: '',
    negociacion: '',
    anticipo: null,
    saldo: null,
    observaciones_mer: ''
  };

  // Despacho seleccionado para editar
  despachoSeleccionado: any = {};

  ngOnInit(): void {
    this.obtenerDespachos();
  }

  /** Trae todos los despachos desde la API */
  async obtenerDespachos() {
    try {
      const response = await api.get('/despachos');
      this.despachos = response.data;
    } catch (error) {
      console.error('Error al obtener despachos:', error);
    }
  }

  /** Abre el modal de edición y carga los datos del despacho */
  abrirFormularioEditar(despacho: any) {
    this.despachoSeleccionado = { ...despacho };
  }

  /** Crea un nuevo despacho */
  async crearDespacho() {
    try {
      await api.post('/despachos', this.nuevaDespacho);
      alert('✅ Despacho creado con éxito');
      this.obtenerDespachos();
      // Reset del formulario
      this.nuevaDespacho = {
        mercancias_id: null,
        vehiculos_id: null,
        usuarios_id: null,
        tipo_pago_id: null,
        fecha_despacho: '',
        negociacion: '',
        anticipo: null,
        saldo: null,
        observaciones_mer: ''
      };
    } catch (error: any) {
      console.error('❌ Error al crear despacho:', error);
      alert('Error al crear despacho: ' + JSON.stringify(error.response?.data || error));
    }
  }

  /** Actualiza el despacho seleccionado */
  async actualizarDespacho() {
    try {
      await api.put(`/despachos/${this.despachoSeleccionado.id}`, this.despachoSeleccionado);
      alert('✅ Despacho actualizado con éxito');
      this.obtenerDespachos();
    } catch (error: any) {
      console.error('❌ Error al actualizar despacho:', error);
      alert('Error al actualizar despacho: ' + JSON.stringify(error.response?.data || error));
    }
  }

  /** Elimina un despacho por su ID */
  async eliminarDespacho(id: number) {
    try {
      await api.delete(`/despachos/${id}`);
      alert('✅ Despacho eliminado');
      this.obtenerDespachos();
    } catch (error) {
      console.error('Error al eliminar despacho:', error);
    }
  }

  /** Consulta un despacho por su ID */
  async consultarDespacho(id: number) {
    try {
      const response = await api.get(`/despachos/${id}`);
      alert(JSON.stringify(response.data, null, 2));
    } catch (error) {
      console.error('Error al consultar despacho:', error);
    }
  }

  /** Placeholder para "Guardar Cambios" global */
  guardarCambios() {
    alert('Función "Guardar Cambios" aún no implementada.');
  }
}
