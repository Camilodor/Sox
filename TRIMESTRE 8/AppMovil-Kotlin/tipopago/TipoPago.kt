package com.example.demo.tipopago

import java.time.LocalDateTime

data class TipoPago(
    val id: Int? = null,
    val nombre: String,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)