package com.example.appinterface

import java.time.LocalDateTime

data class Seguimientos(
    val id: Int? = null,
    val mercancias_id: Int,
    val estado: String,
    val observaciones: String? = null,
    val createdAt: String?= null,
    val updatedAt: String?= null
)