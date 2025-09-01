package com.example.demo.mercancias

import java.time.LocalDateTime

data  class Mercancias (
    val id: Int? = null,
    val proveedor_id: Int? = null,
    val usuario_id: Int? = null,
    val fecha_ingreso: LocalDateTime? =null,
    val num_remesa: String? = null,
    val origen_mer: String,
    val destino_mer: String,

    val nom_remitente: String,
    val doc_remitente: String,
    val direccion_remitente: String,
    val cel_remitente: String,

    val destin_nom: String,
    val destin_doc: String,
    val destin_direccion: String,
    val destin_celular: String,

    val val_declarado: Double,
    val val_flete: Double,
    val val_total: Double,
    val peso: Double,
    val unidades: Int,
    val observaciones: String? = null,

    val tipopago_id: Int? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null


)