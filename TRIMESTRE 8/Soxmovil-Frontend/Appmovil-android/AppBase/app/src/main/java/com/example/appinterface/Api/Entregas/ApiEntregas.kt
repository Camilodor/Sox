package com.example.appinterface.Api.Entregas


import com.example.appinterface.Modelos.Entregas.Entregas
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiEntregas {
    @GET("entregas")
    fun getEntregas(): Call<List<Entregas>>

    @GET("entregas/{id}")
    fun getEntrega(@Path("id") id: Int): Call<Entregas>

    @POST("entregas")
    fun createEntrega(@Body entrega: Entregas): Call<ResponseBody>

    @PUT("entregas/{id}")
    fun updateEntrega(@Path("id") id: Int, @Body entrega: Entregas): Call<ResponseBody>

    @DELETE("entregas/{id}")
    fun deleteEntrega(@Path("id") id: Int): Call<ResponseBody>
}
