import { Routes } from '@angular/router';
import { CuerpoComponent } from './cuerpo/cuerpo.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { PaginaComponent } from './pagina/pagina.component';
import { LoginComponent } from './login/login.component';

import { CategoriasComponent } from './categorias/categorias.component';
import { SubcategoriasComponent } from './subcategorias/subcategorias.component';
import { MercanciaComponent } from './mercancia/mercancia.component';
import { DespachoComponent } from './despacho/despacho.component';
import { EntregasComponent } from './entregas/entregas.component';
import { DevolucionesComponent } from './devoluciones/devoluciones.component';
import { VehiculosComponent } from './vehiculos/vehiculos.component';
import { GestionarusuarioComponent } from './gestionarusuario/gestionarusuario.component';




export const routes: Routes = [
  {path: '', redirectTo: 'sox', pathMatch: 'full'},
  {path:'login',component:LoginComponent},
  { path: 'usuarios', component: UsuariosComponent },
    { path: 'categorias', component: CategoriasComponent },
    { path: 'subcategorias', component: SubcategoriasComponent },
    { path: 'mercancia', component: MercanciaComponent },
    { path: 'despacho', component: DespachoComponent},
    { path: 'entrega', component: EntregasComponent },
    { path: 'devoluciones', component: DevolucionesComponent },
    { path: 'vehiculos', component: VehiculosComponent },
    { path: 'gestionar', component:GestionarusuarioComponent  },


  {path: 'adso', component: CuerpoComponent,
    children:[
      {path: '', component: PaginaComponent },
     
      { path: 'pagina',component: PaginaComponent},
    ]
  },
  {path:'**',redirectTo: 'adso'},
]
