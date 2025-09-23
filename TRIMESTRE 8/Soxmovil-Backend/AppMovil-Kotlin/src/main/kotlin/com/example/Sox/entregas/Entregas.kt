package com.example.Sox.entregas

import java.time.LocalDateTime

data class Entregas (
    val id: Int? = null,
    val mercancias_id: Int,
    val despachos_id: Int,
    val usuarios_id: Int,
    val nombre_recibe: String,
    val numero_celular_recibe: String,
    val observaciones: String? = null,
    val fecha_entrega: LocalDateTime,
    val estado_entrega: String = "Pendiente",
    val createdAt: String? = null,
    val updatedAt: String? = null
)