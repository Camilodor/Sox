package com.example.appinterface

import java.time.LocalDateTime

data class Despachos (
    val id: Int? = null,
    val mercancias_id: Int,
    val vehiculos_id: Int,
    val usuarios_id: Int,
    val tipo_pago_id: Int,
    val fecha_despacho: String,
    val negociacion: Double,
    val anticipo: Double,
    val saldo: Double,
    val observaciones_mer: String? = null,

)