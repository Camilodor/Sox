package com.example.appinterface.Api

import com.example.appinterface.TipoPago
import com.example.appinterface.Usuarios
import retrofit2.Call
import retrofit2.http.*

interface ApiTipoPago {
    @GET("tipospago")
    fun getTipospago(): Call<List<TipoPago>>

    @GET("tipospago/{id}")
    fun getTipopagoPorId(@Path("id") id: Int): Call<TipoPago>

    @POST("tipospago")
    fun crearTipopago(@Body tipopago: TipoPago): Call<String>

    @PUT("tipospago/{id}")
    fun actualizarTipopago(@Path("id") id: Int, @Body tipopago: TipoPago): Call<String>

    @DELETE("tipospago/{id}")
    fun eliminarTipopago(@Path("id") id: Int): Call<String>
}