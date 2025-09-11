import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { Admin } from './dashboards/admin/admin.component';
import { Conductor } from './dashboards/conductor/conductor.component';
import { Bodega } from './dashboards/bodega/bodega.component';      
import { Cliente } from './dashboards/cliente/cliente.component';
import { AccesoDenegadoComponent } from './acceso-denegado/acceso-denegado.componet';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { BaseLayoutComponent } from './layouts/base-layout/base-layout.component';
import { ProveedoresComponent } from './proveedores/proveedores.component';
import { ProductosComponent } from './productos/productos.component';
import { MercanciasComponent } from './mercancias/mercancias.component';
import { AuthGuard } from './guards/auth-guard';
import { VehiculosComponent } from './vehiculos/vehiculos.component';
import { DespachosComponent } from './despachos/despachos.component';
import { EntregasComponent } from './entregas/entregas.component';
import { DevolucionesComponent } from './devoluciones/devoluciones.component';
import { SeguimientosComponent } from './seguimientos/seguimientos.component';
import { TiposrolComponent } from './tiposrol/tiposrol.component';
import { TiposdocumentoComponent } from './tiposdocumento/tiposdocumento.component';
import { TipospagoComponent } from './tipospago/tipospago.component';


export const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
  path: 'admin',
  component: Admin,
  canActivate: [AuthGuard],
  data: { roles: ['Administrador'] },
},


  {
  path: '',
  component: BaseLayoutComponent, // <- Aquí va tu dashboard general
  canActivate: [AuthGuard],
  data: { roles: ['Administrador'] },
  children: [
    { path: 'usuarios', component: UsuariosComponent },
    { path: 'proveedores', component: ProveedoresComponent },
    { path: 'productos', component: ProductosComponent },
    { path: 'mercancias', component: MercanciasComponent },
    { path: 'vehiculos', component: VehiculosComponent },
    { path: 'despachos', component: DespachosComponent },
    { path: 'entregas', component: EntregasComponent },
    { path: 'devoluciones', component: DevolucionesComponent },
    { path: 'seguimientos', component: SeguimientosComponent },
    { path: 'tiporol', component: TiposrolComponent },
    { path: 'tipodocumentos', component: TiposdocumentoComponent },
    { path: 'tipospago', component: TipospagoComponent }
    
    
 
  ]
},



  { 
    path: 'conductor', 
    component: Conductor, 
    canActivate: [AuthGuard],
    data: { roles: ['Conductor'] } 
  },
  { 
    path: 'bodeguero', 
    component: Bodega, 
    canActivate: [AuthGuard],
    data: { roles: ['Bodeguero'] } 
  },
  { 
    path: 'cliente', 
    component: Cliente, 
    canActivate: [AuthGuard],
    data: { roles: ['Cliente'] } 
  },
  {
    path: 'acceso-denegado',
    loadComponent: () => import('./acceso-denegado/acceso-denegado.componet')
      .then(m => m.AccesoDenegadoComponent)
  },

  { path: '**', redirectTo: 'login' }
];
