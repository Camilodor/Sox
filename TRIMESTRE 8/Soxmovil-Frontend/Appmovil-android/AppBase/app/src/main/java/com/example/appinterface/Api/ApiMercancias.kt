package com.example.appinterface.Api

import com.example.appinterface.Usuarios
import retrofit2.Call
import retrofit2.http.*

interface ApiMercancias {
    @GET("mercancias")
    fun getMercancias(): Call<List<Mercancias>>

    @GET("mercancias/{id}")
    fun getMercanciaPorId(@Path("id") id: Int): Call<Mercancias>

    @POST("mercancias")
    fun crearMercancia(@Body mercancia: Mercancias): Call<String>

    @PUT("mercancias/{id}")
    fun actualizarMercancia(@Path("id") id: Int, @Body mercancia: Mercancias): Call<String>

    @DELETE("mercancias/{id}")
    fun eliminarMercancia(@Path("id") id: Int): Call<String>
}