package com.example.appinterface.Api

import com.example.appinterface.Usuarios
import retrofit2.Call
import retrofit2.http.*

interface ApiProveedores {
    @GET("proveedores")
    fun getProveedores(): Call<List<Proveedores>>

    @GET("proveedores/{id}")
    fun getProveedorPorId(@Path("id") id: Int): Call<Proveedores>

    @POST("proveedores")
    fun crearProveedor(@Body proveedor: Proveedores): Call<String>

    @PUT("proveedores/{id}")
    fun actualizarProveedor(@Path("id") id: Int, @Body proveedor: Proveedores): Call<String>

    @DELETE("provedores/{id}")
    fun eliminarProveedor(@Path("id") id: Int): Call<String>
}