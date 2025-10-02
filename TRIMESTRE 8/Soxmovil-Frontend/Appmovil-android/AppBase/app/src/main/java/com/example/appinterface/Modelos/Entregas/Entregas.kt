package com.example.appinterface.Modelos.Entregas

import java.io.Serializable

data class Entregas(
    val id: Int,
    val mercancias_id: Int,
    val despachos_id: Int,
    val usuarios_id: Int,
    val nombre_recibe: String,
    val numero_celular_recibe: String,
    val fecha_entrega: String,
    val estado_entrega: String,
    val observaciones: String?
):Serializable
