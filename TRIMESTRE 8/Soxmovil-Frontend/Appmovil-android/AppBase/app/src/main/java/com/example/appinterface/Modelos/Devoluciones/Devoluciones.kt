package com.example.appinterface.Modelos.Devoluciones

import java.io.Serializable

data class Devoluciones (
    val id: Int? = null,
    val mercancias_id: Int,
    val despachos_id: Int,
    val usuarios_id: Int,
    val fecha_devolucion: String,
    val motivo_devolucion: String,
    val estado_devolucion: String,
    val observaciones: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
    ): Serializable