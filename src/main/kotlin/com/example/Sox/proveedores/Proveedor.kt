package com.example.Sox.proveedores

data class Proveedor(
    val id: Int? = null,        // nullable porque al crear aún no existe
    val nombre: String,
    val descripcion: String? = null
)