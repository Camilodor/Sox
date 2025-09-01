package com.example.demo.usuarios

data class Usuarios (
    val id: Int? = null, // nullable porque al crear aún no existe
    val nombre_usuario: String,
    val nombres: String,
    val apellidos: String,
    val tipo_documento_id: Int,
    val numero_documento: Long,
    val telefono: String,
    val direccion: String,
    val ciudad: String,
    val email: String,
    val contrasena: String,
    val tiposrol_id: Int
)