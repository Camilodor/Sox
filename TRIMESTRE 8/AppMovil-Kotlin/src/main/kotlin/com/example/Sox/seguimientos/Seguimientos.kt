package com.example.Sox.seguimientos


import java.time.LocalDateTime

data class Seguimientos (

    val id: Int? = null,
    val mercancias_id: Int,
    val despacho_id: Int? = null,
    val entrega_id: Int? = null,
    val devolucion_id: Int? = null,
    val usuario_id: Int,
    val evento: String,
    val estado: String,
    val createdAt: LocalDateTime?= null,
    val updatedAt: LocalDateTime?= null


)