package com.example.appinterface.Api.Seguimientos

import com.example.appinterface.Modelos.Seguimientos.Seguimientos
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiSeguimientos {
    @GET("seguimientos")
    fun getSeguimientos(): Call<List<Seguimientos>>

    @GET("seguimientos/{id}")
    fun getSeguimientoPorId(@Path("id") id: Int): Call<Seguimientos>

    @POST("seguimientos")
    fun crearSeguimiento(@Body seguimiento: Seguimientos): Call<ResponseBody>

    @PUT("proveedores/{id}")
    fun actualizarSeguimiento(@Path("id") id: Int, @Body seguimiento: Seguimientos): Call<ResponseBody>

    @DELETE("provedores/{id}")
    fun eliminarSeguimiento(@Path("id") id: Int): Call<ResponseBody>
}