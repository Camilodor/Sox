package com.example.Sox.seguimientos


import java.time.LocalDateTime

data class Seguimientos (

    val id: Int? = null,
    val mercancias_id: Int,
    val estado: String,
    val observaciones: String? = null,
    val createdAt: LocalDateTime?= null,
    val updatedAt: LocalDateTime?= null


)