package com.example.appinterface.Api

import com.example.appinterface.Despachos
import retrofit2.Call
import retrofit2.http.*

interface ApiDespachos {
    @GET("despachos")
    fun getDespachos(): Call<List<Despachos>>

    @GET("despachos/{id}")
    fun getDespachoPorId(@Path("id") id: Int): Call<Despachos>

    @POST("despachos")
    fun crearDespacho(@Body despacho: Despachos): Call<String>

    @PUT("despachos/{id}")
    fun actualizarDespacho(@Path("id") id: Int, @Body despacho: Despachos): Call<String>

    @DELETE("despachos/{id}")
    fun eliminarDespacho(@Path("id") id: Int): Call<String>
}