package com.example.appinterface.Modelos.Mercancias

import java.io.Serializable

data class Mercancias(
    val id: Int? = null,
    val proveedores_id: Int,
    val usuarios_id: Int,
    val fecha_ingreso: String,
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
): Serializable