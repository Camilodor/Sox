package com.example.appinterface.Api

import retrofit2.Call
import retrofit2.http.GET

interface ApiServicesKotlin {
    @GET("/usuarios")
    fun getPersonas(): Call<List<String>>
}