import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from '../nav/nav.component';
import { VerticalNavComponent } from '../vertical-nav/vertical-nav.component';

@Component({
  selector: 'app-subcategorias',
  standalone: true,
  imports: [CommonModule,NavComponent,VerticalNavComponent],
  templateUrl: './subcategorias.component.html',
  styleUrl: './subcategorias.component.css'
})
export class SubcategoriasComponent {
  subcategorias = [
    { id: 1, nombre: 'Teléfonos', },
    { id: 2, nombre: 'Computadoras',  },
    { id: 3, nombre: 'Camisetas',  },
    { id: 4, nombre: 'Pantalones',  },
    { id: 5, nombre: 'Cereales',  },
    { id: 6, nombre: 'Lácteos',  },
    { id: 7, nombre: 'Sofás',  },
    { id: 8, nombre: 'Mesas', },
    { id: 9, nombre: 'Muñecas',  },
    { id: 10, nombre: 'Juegos de mesa', }
  ];

  subcategoriaSeleccionada: any;

  abrirFormularioEditar(subcategoria: any) {
    this.subcategoriaSeleccionada = subcategoria;
  }

  eliminarSubcategoria(id: number) {
    alert(`Subcategoría con ID: ${id} eliminada`);
   
  }

  consultarSubcategoria(id: number) {
    alert(`Consultar subcategoría con ID: ${id}`);
   
  }

  guardarCambios() {
    alert('Cambios guardados correctamente');
   
  }
}