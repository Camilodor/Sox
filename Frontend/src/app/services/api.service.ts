import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'http://127.0.0.1:8000/api';

  constructor(private http: HttpClient) {}

  // ✅ Usuarios
  getUsuarios(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/usuarios`);
  }

  getUsuario(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/usuarios/${id}`);
  }

  crearUsuario(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/usuarios`, data);
  }

  actualizarUsuario(id: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/usuarios/${id}`, data);
  }

  eliminarUsuario(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/usuarios/${id}`);
  }

  // ✅ Roles
  getTiposRol(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/tiporol`);
  }

  // ✅ Documentos
  getTiposDocumento(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/tipodocumentos`);
  }
  getProveedores() {
  return this.http.get<any[]>(`${this.apiUrl}/proveedores`);
}

getProveedor(id: number) {
  return this.http.get<any>(`${this.apiUrl}/proveedores/${id}`);
}

crearProveedor(data: any) {
  return this.http.post(`${this.apiUrl}/proveedores`, data);
}

actualizarProveedor(id: number, data: any) {
  return this.http.put(`${this.apiUrl}/proveedores/${id}`, data);
}

eliminarProveedor(id: number) {
  return this.http.delete(`${this.apiUrl}/proveedores/${id}`);
}
    getProductos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/productos`);
  }

  getProducto(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/productos/${id}`);
  }

  crearProducto(data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/productos`, data);
  }

  actualizarProducto(id: number, data: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/productos/${id}`, data);
  }

  eliminarProducto(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/productos/${id}`);
  }
  // ✅ Mercancias
getMercancias(): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/mercancias`);
}

getMercancia(id: number): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/mercancias/${id}`);
}

crearMercancia(data: any): Observable<any> {
  return this.http.post<any>(`${this.apiUrl}/mercancias`, data);
}

actualizarMercancia(id: number, data: any): Observable<any> {
  return this.http.put<any>(`${this.apiUrl}/mercancias/${id}`, data);
}

eliminarMercancia(id: number): Observable<any> {
  return this.http.delete<any>(`${this.apiUrl}/mercancias/${id}`);
}

// ✅ Tipos de Pago
getTiposPago(): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/tipospago`);
}
// ✅ Vehículos
getVehiculos(): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/vehiculos`);
}

getVehiculo(id: number): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/vehiculos/${id}`);
}

crearVehiculo(data: any): Observable<any> {
  return this.http.post<any>(`${this.apiUrl}/vehiculos`, data);
}

actualizarVehiculo(id: number, data: any): Observable<any> {
  return this.http.put<any>(`${this.apiUrl}/vehiculos/${id}`, data);
}

eliminarVehiculo(id: number): Observable<any> {
  return this.http.delete<any>(`${this.apiUrl}/vehiculos/${id}`);
}
 getDespachos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/despachos`);
  }

  getDespacho(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/despachos/${id}`);
  }

  crearDespacho(despacho: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/despachos`, despacho);
  }

  actualizarDespacho(id: number, despacho: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/despachos/${id}`, despacho);
  }

  eliminarDespacho(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/despachos/${id}`);
  }
  // 📦 Entregas
// 📦 Entregas
getEntregas(): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/entregas`);
}

getEntrega(id: number): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/entregas/${id}`);
}

crearEntrega(entrega: any): Observable<any> {
  return this.http.post<any>(`${this.apiUrl}/entregas`, entrega);
}

actualizarEntrega(id: number, entrega: any): Observable<any> {
  return this.http.put<any>(`${this.apiUrl}/entregas/${id}`, entrega);
}

eliminarEntrega(id: number): Observable<any> {
  return this.http.delete<any>(`${this.apiUrl}/entregas/${id}`);
}
getDevoluciones(): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/devoluciones`);
}

getDevolucion(id: number): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/devoluciones/${id}`);
}

crearDevolucion(devolucion: any): Observable<any> {
  return this.http.post<any>(`${this.apiUrl}/devoluciones`, devolucion);
}

actualizarDevolucion(id: number, devolucion: any): Observable<any> {
  return this.http.put<any>(`${this.apiUrl}/devoluciones/${id}`, devolucion);
}

eliminarDevolucion(id: number): Observable<any> {
  return this.http.delete<any>(`${this.apiUrl}/devoluciones/${id}`);
}
// 📦 Seguimientos
getSeguimientos(): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/seguimientos`);
}

getSeguimiento(id: number): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/seguimientos/${id}`);
}

crearSeguimiento(seguimiento: any): Observable<any> {
  return this.http.post<any>(`${this.apiUrl}/seguimientos`, seguimiento);
}

actualizarSeguimiento(id: number, seguimiento: any): Observable<any> {
  return this.http.put<any>(`${this.apiUrl}/seguimientos/${id}`, seguimiento);
}

eliminarSeguimiento(id: number): Observable<any> {
  return this.http.delete<any>(`${this.apiUrl}/seguimientos/${id}`);
}

// 📌 Tipos Rol

getTipoRol(id: number): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/tiporol/${id}`);
}

crearTipoRol(tiporol: any): Observable<any> {
  return this.http.post<any>(`${this.apiUrl}/tiporol`, tiporol);
}

actualizarTipoRol(id: number, tiporol: any): Observable<any> {
  return this.http.put<any>(`${this.apiUrl}/tiporol/${id}`, tiporol);
}

eliminarTipoRol(id: number): Observable<any> {
  return this.http.delete<any>(`${this.apiUrl}/tiporol/${id}`);
}

// 📌 Tipos de Pago

getTipoPago(id: number): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/tipospago/${id}`);
}

crearTipoPago(tipopago: any): Observable<any> {
  return this.http.post<any>(`${this.apiUrl}/tipospago`, tipopago);
}

actualizarTipoPago(id: number, tipopago: any): Observable<any> {
  return this.http.put<any>(`${this.apiUrl}/tipospago/${id}`, tipopago);
}

eliminarTipoPago(id: number): Observable<any> {
  return this.http.delete<any>(`${this.apiUrl}/tipospago/${id}`);
}

// 📌 Tipos de Documento

getTipoDocumento(id: number): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/tipodocumentos/${id}`);
}

crearTipoDocumento(tipodoc: any): Observable<any> {
  return this.http.post<any>(`${this.apiUrl}/tipodocumentos`, tipodoc);
}

actualizarTipoDocumento(id: number, tipodoc: any): Observable<any> {
  return this.http.put<any>(`${this.apiUrl}/tipodocumentos/${id}`, tipodoc);
}

eliminarTipoDocumento(id: number): Observable<any> {
  return this.http.delete<any>(`${this.apiUrl}/tipodocumentos/${id}`);
}


  // 🔹 Puedes agregar aquí otros endpoints: vehículos, mercancias, r_personales, etc.
}
