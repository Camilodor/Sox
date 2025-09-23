package com.example.Sox.mercancias

import java.time.LocalDate

data class Mercancia(
    val id: Int? = null,
    val proveedores_id: Int,
    val usuarios_id: Int,
    val fecha_ingreso: LocalDate,
    val numero_remesa: String,
    val origen_mercancia: String,
    val destino_mercancia: String,

    val nombre_remitente: String,
    val documento_remitente: String,
    val direccion_remitente: String,
    val celular_remitente: String,

    val nombre_destinatario: String,
    val documento_destinatario: String,
    val direccion_destinatario: String,
    val celular_destinatario: String,

    val valor_declarado: Double,
    val valor_flete: Double,
    val valor_total: Double,
    val peso: Double,
    val unidades: Int,
    val observaciones: String? = null,

    val tipo_pago_id: Int
)
