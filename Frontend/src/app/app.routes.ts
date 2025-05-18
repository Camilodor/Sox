import { Routes } from '@angular/router';
import { CuerpoComponent } from './cuerpo/cuerpo.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { PaginaComponent } from './pagina/pagina.component';
import { LoginComponent } from './login/login.component';
import { GestionarusuarioComponent } from './gestionarusuario/gestionarusuario.component';




export const routes: Routes = [
  {path: '', redirectTo: 'sox', pathMatch: 'full'},
  {path:'login',component:LoginComponent},
  { path: 'usuarios', component: UsuariosComponent },
    { path: 'gestionar', component:GestionarusuarioComponent  },
      { path: 'r-personales', loadComponent: () => import('./r-personales/r-personales.component').then(m => m.RPersonalesComponent) },
  { path: 'r-laborales', loadComponent: () => import('./r-laborales/r-laborales.component').then(m => m.RLaboralesComponent) },
  { path: 'proveedores', loadComponent: () => import('./proveedores/proveedores.component').then(m => m.ProveedoresComponent) },
  { path: 'productos', loadComponent: () => import('./productos/productos.component').then(m => m.ProductosComponent) },
  { path: 'mercancias', loadComponent: () => import('./mercancias/mercancias.component').then(m => m.MercanciasComponent) },
  { path: 'vehiculos', loadComponent: () => import('./vehiculos/vehiculos.component').then(m => m.VehiculosComponent) },
  { path: 'despachos', loadComponent: () => import('./despachos/despachos.component').then(m => m.DespachosComponent) },
  { path: 'entregas', loadComponent: () => import('./entregas/entregas.component').then(m => m.EntregasComponent) },
  { path: 'devoluciones', loadComponent: () => import('./devoluciones/devoluciones.component').then(m => m.DevolucionesComponent) },
  { path: 'seguimientos', loadComponent: () => import('./seguimientos/seguimientos.component').then(m => m.SeguimientosComponent) },
  { path: 'tipo-documento', loadComponent: () => import('./tipo-documento/tipo-documento.component').then(m => m.TipoDocumentoComponent) },
  { path: 'tipos-rol', loadComponent: () => import('./tipos-rol/tipos-rol.component').then(m => m.TipoRolComponent) },
  { path: 'tipos-pago', loadComponent: () => import('./tipos-pago/tipos-pago.component').then(m => m.TiposPagoComponent) },

  {path: 'adso', component: CuerpoComponent,
    children:[
      {path: '', component: PaginaComponent },
     
      { path: 'pagina',component: PaginaComponent},
    ]
  },
  {path:'**',redirectTo: 'adso'},
]
