package com.example.Sox.usuarios


import java.time.LocalDateTime

data class Usuarios (
    val id: Int? = null,
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