package com.example.appinterface.Api

import com.example.appinterface.Devoluciones
import com.example.appinterface.Entregas
import retrofit2.Call
import retrofit2.http.*

interface ApiDevoluciones {
    @GET("devoluciones")
    fun getDevoluciones(): Call<List<Devoluciones>>

    @GET("devoluciones/{id}")
    fun getDevolucionPorId(@Path("id") id: Int): Call<Devoluciones>

    @POST("devoluciones")
    fun crearDevolucion(@Body devolucion: Devoluciones): Call<String>

    @PUT("devoluciones/{id}")
    fun actualizarDevolucion(@Path("id") id: Int, @Body devolucion: Devoluciones): Call<String>

    @DELETE("devoluciones/{id}")
    fun eliminarDevolucion(@Path("id") id: Int): Call<String>
}