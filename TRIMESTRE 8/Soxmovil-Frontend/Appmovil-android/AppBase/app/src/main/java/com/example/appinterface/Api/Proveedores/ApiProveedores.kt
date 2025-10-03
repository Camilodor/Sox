package com.example.appinterface.Api.Proveedores

import com.example.appinterface.Modelos.Proveedores.Proveedores
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiProveedores {
    @GET("proveedores")
    fun getProveedores(): Call<List<Proveedores>>

    @GET("proveedores/{id}")
    fun getProveedor(@Path("id") id: Int): Call<Proveedores>


    @POST("proveedores")
    fun createProveedor(@Body proveedor: Proveedores): Call<ResponseBody>

    @PUT("proveedores/{id}")
    fun updateProveedor(@Path("id") id: Int, @Body proveedor: Proveedores): Call<ResponseBody>

    @DELETE("proveedores/{id}")
    fun deleteProveedor(@Path("id") id: Int): Call<ResponseBody>
}