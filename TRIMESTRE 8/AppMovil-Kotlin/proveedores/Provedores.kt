package com.example.demo.proveedores

import java.time.LocalDateTime

data class Provedores(
    val id: Int,
    val nombre: String,
    val descripcion: String? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)