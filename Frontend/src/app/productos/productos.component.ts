import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';
import api from '../services/api.services';

declare const bootstrap: any;

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule, FormsModule, NavComponent, VerticalNavComponent],
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css']
})
export class ProductosComponent implements OnInit {
  productos: any[] = [];
  productoSeleccionado: any = {};
  nuevoProducto: any = {
    proveedores_id: null,
    nombre: '',
    descripcion: ''
  };

  ngOnInit(): void {
    this.obtenerProductos();
  }

  async obtenerProductos() {
    try {
      const response = await api.get('/productos');
      this.productos = response.data;
    } catch (error) {
      console.error('Error al obtener productos:', error);
    }
  }

  abrirFormularioEditar(producto: any) {
    this.productoSeleccionado = { ...producto };
  }

  async crearProducto() {
    try {
      await api.post('/productos', this.nuevoProducto);
      alert('✅ Producto creado con éxito');
      this.obtenerProductos();
      this.nuevoProducto = { proveedores_id: null, nombre: '', descripcion: '' };
    } catch (error: any) {
      console.error('❌ Error al crear producto:', error);
      if (error.response?.data) {
        alert('Error al crear producto: ' + JSON.stringify(error.response.data));
      }
    }
  }

  async actualizarProducto() {
    try {
      await api.put(`/productos/${this.productoSeleccionado.id}`, this.productoSeleccionado);
      alert('✅ Producto actualizado con éxito');
      this.obtenerProductos();
    } catch (error: any) {
      console.error('❌ Error al actualizar producto:', error);
      if (error.response?.data) {
        alert('Error al actualizar producto: ' + JSON.stringify(error.response.data));
      }
    }
  }

  async eliminarProducto(id: number) {
    try {
      await api.delete(`/productos/${id}`);
      alert('✅ Producto eliminado');
      this.obtenerProductos();
    } catch (error) {
      console.error('Error al eliminar producto:', error);
    }
  }

  async consultarProducto(id: number) {
    try {
      const response = await api.get(`/productos/${id}`);
      this.productoSeleccionado = response.data;
      const modalEl = document.getElementById('consultModal');
      if (modalEl) new bootstrap.Modal(modalEl).show();
    } catch (error) {
      console.error('Error al consultar producto:', error);
    }
  }

  async guardarCambios() {
    alert('Función guardar aún no implementada.');
  }
}
