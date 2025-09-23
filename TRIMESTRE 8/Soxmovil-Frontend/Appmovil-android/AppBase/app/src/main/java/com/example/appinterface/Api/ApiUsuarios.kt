package com.example.appinterface.Api

import com.example.appinterface.Usuarios
import retrofit2.Call
import retrofit2.http.*

interface ApiUsuarios {
    @GET("usuarios")
    fun getUsuarios(): Call<List<Usuarios>>

    @GET("usuarios/{id}")
    fun getUsuarioPorId(@Path("id") id: Int): Call<Usuarios>

    @POST("usuarios")
    fun crearUsuario(@Body usuario: Usuarios): Call<String>

    @PUT("usuarios/{id}")
    fun actualizarUsuario(@Path("id") id: Int, @Body usuario: Usuarios): Call<String>

    @DELETE("usuarios/{id}")
    fun eliminarUsuario(@Path("id") id: Int): Call<String>
}