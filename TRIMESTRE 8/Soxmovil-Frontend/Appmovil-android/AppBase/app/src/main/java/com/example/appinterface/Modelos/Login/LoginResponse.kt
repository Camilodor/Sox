package com.example.appinterface.Modelos.Login

data class LoginResponse(
    val access_token: String,
    val token_type: String,
    val user: User
)
