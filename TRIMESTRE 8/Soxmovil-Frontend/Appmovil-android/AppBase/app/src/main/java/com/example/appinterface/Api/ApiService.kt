package com.example.appinterface.Api

import com.example.appinterface.Modelos.Login.User
import com.example.appinterface.Modelos.Login.LoginRequest
import com.example.appinterface.Modelos.Login.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("login-movil")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("me-movil")
    fun me(@Header("Authorization") token: String): Call<User>

    @POST("logout-movil")
    fun logout(@Header("Authorization") token: String): Call<ApiResponse>
}
