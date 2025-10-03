package com.example.appinterface.Modelos.Seguimientos

import java.io.Serializable

data class Seguimientos(
    val id: Int? = null,
    val mercancias_id: Int,
    val estado: String,
    val observaciones: String? = null,
    val createdAt: String?= null,
    val updatedAt: String?= null
): Serializable