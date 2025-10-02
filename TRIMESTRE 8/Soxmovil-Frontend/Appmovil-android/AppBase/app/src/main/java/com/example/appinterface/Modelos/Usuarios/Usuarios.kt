package com.example.appinterface.Modelos.Usuarios
import java.io.Serializable

data class Usuarios (
    val id: Int,
    val nombre_usuario: String,
    val nombres: String,
    val apellidos: String,
    val tipo_documento_id: Int,
    val numero_documento: Long,
    val celular: String,
    val direccion: String,
    val ciudad: String,
    val email: String,
    val contrasena: String,
    val tipo_rol_id: Int
) : Serializable

