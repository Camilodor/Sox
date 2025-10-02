package com.example.appinterface.Modelos.Proveedores

import java.io.Serializable

data class Proveedores (
    val id: Int? = null,
    val usuarios_id: Int,
    val nombre: String,
    val descripcion: String? = null
):Serializable