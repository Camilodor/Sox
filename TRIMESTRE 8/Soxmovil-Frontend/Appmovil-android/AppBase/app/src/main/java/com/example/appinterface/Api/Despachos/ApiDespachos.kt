package com.example.appinterface.Api.Despachos

import com.example.appinterface.Modelos.Despachos.Despachos
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiDespachos {
    @GET("despachos")
    fun getDespachos(): Call<List<Despachos>>

    @GET("despachos/{id}")
    fun getDespacho(@Path("id") id: Int): Call<Despachos>

    @PUT("despachos/{id}")
    fun updateDespacho(@Path("id") id: Int, @Body despachos: Despachos): Call<ResponseBody>

}