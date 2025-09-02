package com.example.Sox.devoluciones


import java.time.LocalDateTime

data class Devoluciones (

    val id: Int? = null,
    val mercancias_id: Int,
    val usuarios_id: Int,
    val proveedores_id: Int,
    val fecha_devolucion: LocalDateTime,
    val motivo_devolucion: String,
    val estado_devolucion: String,
    val observaciones: String? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)