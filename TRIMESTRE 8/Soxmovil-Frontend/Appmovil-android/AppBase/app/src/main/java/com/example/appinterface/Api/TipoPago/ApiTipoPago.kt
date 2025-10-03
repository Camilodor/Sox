package com.example.appinterface.Api.TipoPago

import com.example.appinterface.Modelos.TipoPago.TipoPago
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiTipoPago {
    @GET("tipospago")
    fun getTipospago(): Call<List<TipoPago>>

    @GET("tipospago/{id}")
    fun getTipopagoPorId(@Path("id") id: Int): Call<TipoPago>


    @PUT("tipospago/{id}")
    fun updateTipopago(@Path("id") id: Int, @Body tipopago: TipoPago): Call<ResponseBody>


}