package com.example.appinterface.Modelos.TipoPago

import java.io.Serializable

data class TipoPago(
    val id: Int? = null,
    val nombre: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
): Serializable