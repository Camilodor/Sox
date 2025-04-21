import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';

@Component({
  selector: 'app-categorias',
  standalone: true,
  imports: [CommonModule,NavComponent, VerticalNavComponent],
  templateUrl: './categorias.component.html',
  styleUrl: './categorias.component.css'
})
export class CategoriasComponent {

  categorias = [
    { id: 1, nombre: 'Electrónica', proveedor: 'Proveedor A' },
    { id: 2, nombre: 'Ropa', proveedor: 'Proveedor B' },
    { id: 3, nombre: 'Alimentos', proveedor: 'Proveedor C' },
    { id: 4, nombre: 'Hogar', proveedor: 'Proveedor D' },
    { id: 5, nombre: 'Juguetes', proveedor: 'Proveedor E' },
    { id: 6, nombre: 'Belleza', proveedor: 'Proveedor F' },
    { id: 7, nombre: 'Libros', proveedor: 'Proveedor G' },
    { id: 8, nombre: 'Herramientas', proveedor: 'Proveedor H' },
    { id: 9, nombre: 'Deportes', proveedor: 'Proveedor I' },
    { id: 10, nombre: 'Automóvil', proveedor: 'Proveedor J' }
  ];

  categoriaSeleccionada: any;

  abrirFormularioEditar(categoria: any) {
    this.categoriaSeleccionada = categoria;
  }

  eliminarCategoria(id: number) {
    alert(`Categoría con ID: ${id} eliminada`);
    
  }

  consultarCategoria(id: number) {
    alert(`Consultar categoría con ID: ${id}`);
    
  }

  guardarCambios() {
    alert('Cambios guardados correctamente');
    
  }
}