import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';
import api from '../services/api.services';

declare const bootstrap: any;

@Component({
  selector: 'app-mercancias',
  standalone: true,
  imports: [CommonModule, FormsModule, NavComponent, VerticalNavComponent],
  templateUrl: './mercancias.component.html',
  styleUrls: ['./mercancias.component.css']
})
export class MercanciasComponent implements OnInit {
  mercancias: any[] = [];
  mercanciaSeleccionada: any = {};
  nuevaMercancia: any = {
    proveedor_id: "",
    usuario_id: "",
    fecha_ingreso: '',
    num_remesa: '',
    origen_mer: '',
    destino_mer: '',
    nom_remitente: '',
    doc_remitente: '',
    direccion_remitente: '',
    cel_remitente: '',
    destin_nom: '',
    destin_doc: '',
    destin_direccion: '',
    destin_celular: '',
    val_declarado: '',
    val_flete: '',
    val_total: '',
    peso: '',
    unidades: "",
    observaciones: '',
    tipopago_id: ""
  };

  ngOnInit(): void {
    this.obtenerMercancias();
  }

  async obtenerMercancias() {
    try {
      const resp = await api.get('/mercancias');
      this.mercancias = resp.data;
    } catch (e) {
      console.error('Error al obtener mercancías:', e);
    }
  }

  abrirFormularioEditar(m: any) {
    this.mercanciaSeleccionada = { ...m };
  }

  async crearMercancia() {
    try {
      await api.post('/mercancias', this.nuevaMercancia);
      alert('✅ Mercancía creada con éxito');
      this.obtenerMercancias();
      // reset
      this.nuevaMercancia = {
        proveedor_id: null, usuario_id: null, fecha_ingreso: '',
        num_remesa: '', origen_mer: '', destino_mer: '',
        nom_remitente: '', doc_remitente: '', direccion_remitente: '',
        cel_remitente: '', destin_nom: '', destin_doc: '',
        destin_direccion: '', destin_celular: '', val_declarado: '',
        val_flete: '', val_total: '', peso: '', unidades: null,
        observaciones: '', tipopago_id: null
      };
    } catch (error: any) {
      console.error('❌ Error al crear mercancía:', error);
      if (error.response?.data) {
        alert('Error: ' + JSON.stringify(error.response.data));
      }
    }
  }

  async actualizarMercancia() {
    try {
      await api.put(`/mercancias/${this.mercanciaSeleccionada.id}`, this.mercanciaSeleccionada);
      alert('✅ Mercancía actualizada con éxito');
      this.obtenerMercancias();
    } catch (error: any) {
      console.error('❌ Error al actualizar mercancía:', error);
      if (error.response?.data) {
        alert('Error: ' + JSON.stringify(error.response.data));
      }
    }
  }

  async eliminarMercancia(id: number) {
    try {
      await api.delete(`/mercancias/${id}`);
      alert('✅ Mercancía eliminada');
      this.obtenerMercancias();
    } catch (e) {
      console.error('Error al eliminar mercancía:', e);
    }
  }

  async consultarMercancia(id: number) {
    try {
      const resp = await api.get(`/mercancias/${id}`);
      this.mercanciaSeleccionada = resp.data;
      const modalEl = document.getElementById('consultModal');
      if (modalEl) new bootstrap.Modal(modalEl).show();
    } catch (e) {
      console.error('Error al consultar mercancía:', e);
    }
  }

  guardarCambios() {
    alert('Función guardar aún no implementada.');
  }
}
