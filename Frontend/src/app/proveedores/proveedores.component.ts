import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';
import { FormsModule } from '@angular/forms';
import api from '../services/api.services';

// Para controlar los modales de Bootstrap
declare const bootstrap: any;

@Component({
  selector: 'app-proveedores',
  standalone: true,
  imports: [CommonModule, NavComponent, VerticalNavComponent, FormsModule],
  templateUrl: './proveedores.component.html',
  styleUrls: ['./proveedores.component.css']
})
export class ProveedoresComponent implements OnInit {
  proveedores: any[] = [];
  proveedorSeleccionado: any = {};
  nuevoProveedor: any = { nombre: '', descripcion: '' };

  ngOnInit(): void {
    this.obtenerProveedores();
  }

  async obtenerProveedores() {
    try {
      const response = await api.get('/proveedores');
      this.proveedores = response.data;
    } catch (error) {
      console.error('Error al obtener proveedores:', error);
    }
  }

  abrirFormularioEditar(proveedor: any) {
    this.proveedorSeleccionado = { ...proveedor };
  }

  async crearProveedor() {
    try {
      await api.post('/proveedores', this.nuevoProveedor);
      alert('✅ Proveedor creado con éxito');
      this.nuevoProveedor = { nombre: '', descripcion: '' };
      this.obtenerProveedores();
    } catch (error: any) {
      console.error('❌ Error al crear proveedor:', error);
      alert('Error: ' + JSON.stringify(error.response?.data || error));
    }
  }

  async actualizarProveedor() {
    try {
      await api.put(`/proveedores/${this.proveedorSeleccionado.id}`, this.proveedorSeleccionado);
      alert('✅ Proveedor actualizado con éxito');
      this.obtenerProveedores();
    } catch (error: any) {
      console.error('❌ Error al actualizar proveedor:', error);
      alert('Error: ' + JSON.stringify(error.response?.data || error));
    }
  }

  async eliminarProveedor(id: number) {
    try {
      await api.delete(`/proveedores/${id}`);
      alert('✅ Proveedor eliminado');
      this.obtenerProveedores();
    } catch (error) {
      console.error('Error al eliminar proveedor:', error);
    }
  }

  async consultarProveedor(id: number) {
    try {
      const response = await api.get(`/proveedores/${id}`);
      this.proveedorSeleccionado = response.data;
      // abrir el modal de forma programática por si no cargara con data-bs-*
      const modalEl = document.getElementById('consultModal');
      if (modalEl) {
        new bootstrap.Modal(modalEl).show();
      }
    } catch (error) {
      console.error('Error al consultar proveedor:', error);
    }
  }
  
  guardarCambios() {
    alert('Función guardar aún no implementada.');
  }
}
