package com.example.appinterface.Api

import com.example.appinterface.Despachos
import com.example.appinterface.Entregas
import retrofit2.Call
import retrofit2.http.*

interface ApiEntregas {
    @GET("entregas")
    fun getEntregas(): Call<List<Entregas>>

    @GET("entregas/{id}")
    fun getEntregaPorId(@Path("id") id: Int): Call<Entregas>

    @POST("entregas")
    fun crearEntrega(@Body entrega: Entregas): Call<String>

    @PUT("entregas/{id}")
    fun actualizarEntrega(@Path("id") id: Int, @Body entrega: Entregas): Call<String>

    @DELETE("entregas/{id}")
    fun eliminarEntrega(@Path("id") id: Int): Call<String>
}