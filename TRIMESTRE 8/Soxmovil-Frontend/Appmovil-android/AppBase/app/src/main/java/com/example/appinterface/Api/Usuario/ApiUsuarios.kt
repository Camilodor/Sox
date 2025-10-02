


package com.example.appinterface.Api.Usuario

import com.example.appinterface.Modelos.Usuarios.Usuarios
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiUsuarios {

    @GET("usuarios")
    fun getUsuarios(): Call<List<Usuarios>>

    @GET("usuarios/{id}")
    fun getUsuario(@Path("id") id: Int): Call<Usuarios>


    @POST("usuarios")
    fun createUsuario(@Body usuario: Usuarios): Call<ResponseBody>

    @PUT("usuarios/{id}")
    fun updateUsuario(@Path("id") id: Int, @Body usuario: Usuarios): Call<ResponseBody>

    @DELETE("usuarios/{id}")
    fun deleteUsuario(@Path("id") id: Int): Call<ResponseBody>
}



