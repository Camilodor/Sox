package com.example.Sox.mercancias

import java.time.LocalDate

data class Mercancia(
    val id: Int? = null,
    val proveedor_id: Int? = null,   // FK a proveedores
    val usuario_id: Int? = null,     // FK a usuarios
    val fecha_ingreso: LocalDate,
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

    val tipopago_id: Int? = null
)
