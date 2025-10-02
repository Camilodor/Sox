package com.example.appinterface.Api.Devoluciones

import com.example.appinterface.Modelos.Devoluciones.Devoluciones
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiDevoluciones {

    @GET("devoluciones")
    fun getDevoluciones(): Call<List<Devoluciones>>

    @GET("devoluciones/{id}")
    fun getDevolucion(@Path("id") id: Int): Call<Devoluciones>


    @POST("devoluciones")
    fun createDevolucion(@Body devolucion: Devoluciones): Call<ResponseBody>

    @PUT("devoluciones/{id}")
    fun updateDevolucion(@Path("id") id: Int, @Body devolucion: Devoluciones): Call<ResponseBody>

    @DELETE("devoluciones/{id}")
    fun deleteDevolucion(@Path("id") id: Int): Call<ResponseBody>
}
