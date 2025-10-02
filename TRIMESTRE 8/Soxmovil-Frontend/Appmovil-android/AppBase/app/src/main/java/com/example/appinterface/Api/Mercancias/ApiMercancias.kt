package com.example.appinterface.Api.Mercancias

import com.example.appinterface.Modelos.Mercancias.Mercancias
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiMercancias {
    @GET("mercancias")
    fun getMercancias(): Call<List<Mercancias>>

    @GET("mercancias/{id}")
    fun getMercancia(@Path("id") id: Int): Call<Mercancias>


    @POST("mercancias")
    fun createMercancia(@Body mercancia: Mercancias): Call<ResponseBody>

    @PUT("mercancias/{id}")
    fun updateMercancia(@Path("id") id: Int, @Body mercancia: Mercancias): Call<ResponseBody>

    @DELETE("mercancias/{id}")
    fun deleteMercancia(@Path("id") id: Int): Call<ResponseBody>
}