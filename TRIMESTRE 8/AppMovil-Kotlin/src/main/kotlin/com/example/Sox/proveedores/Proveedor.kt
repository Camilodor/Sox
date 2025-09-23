package com.example.Sox.proveedores

data class Proveedor(
    val id: Int? = null,
    val usuarios_id: Int,
    val nombre: String,
    val descripcion: String? = null
)