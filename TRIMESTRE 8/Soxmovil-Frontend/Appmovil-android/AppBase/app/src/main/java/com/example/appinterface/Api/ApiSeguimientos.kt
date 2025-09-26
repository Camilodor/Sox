package com.example.appinterface.Api

import com.example.appinterface.Seguimientos
import retrofit2.Call
import retrofit2.http.*

interface ApiSeguimientos {
    @GET("seguimientos")
    fun getSeguimientos(): Call<List<Seguimientos>>

    @GET("seguimientos/{id}")
    fun getSeguimientoPorId(@Path("id") id: Int): Call<Seguimientos>

    @POST("seguimientos")
    fun crearSeguimiento(@Body seguimiento: Seguimientos): Call<String>

    @PUT("proveedores/{id}")
    fun actualizarSeguimiento(@Path("id") id: Int, @Body seguimiento: Seguimientos): Call<String>

    @DELETE("provedores/{id}")
    fun eliminarSeguimiento(@Path("id") id: Int): Call<String>
}