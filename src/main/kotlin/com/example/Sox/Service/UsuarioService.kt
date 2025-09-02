package com.example.Sox.Service.UsuarioService


import com.example.Sox.Modelos.Usuarios
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service


@Service
class UsuarioService {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
    fun obtenerUsuarios(): List<Usuarios> {
        val sql = "SELECT * FROM usuarios"
        return jdbcTemplate.query(sql) { rs, _ ->
            Usuarios(
                id = rs.getInt("id"),
                nombre_usuario = rs.getString("nombre_usuario"),
                nombres = rs.getString("nombres"),
                apellidos = rs.getString("apellidos"),
                tipo_documento_id = rs.getInt("tipo_documento_id"),
                numero_documento = rs.getLong("numero_documento"),
                telefono = rs.getString("telefono"),
                direccion = rs.getString("direccion"),
                ciudad = rs.getString("ciudad"),
                email = rs.getString("email"),
                contrasena = rs.getString("contrasena"),
                tiposrol_id = rs.getInt("tiposrol_id")
            )
        }
    }

    fun obtenerUsuarioPorId(id: Int): Usuarios? {
        val sql = "SELECT * FROM usuarios WHERE id = ?"
        return jdbcTemplate.query(sql, arrayOf(id)) { rs, _ ->
            Usuarios(
                id = rs.getInt("id"),
                nombre_usuario = rs.getString("nombre_usuario"),
                nombres = rs.getString("nombres"),
                apellidos = rs.getString("apellidos"),
                tipo_documento_id = rs.getInt("tipo_documento_id"),
                numero_documento = rs.getLong("numero_documento"),
                telefono = rs.getString("telefono"),
                direccion = rs.getString("direccion"),
                ciudad = rs.getString("ciudad"),
                email = rs.getString("email"),
                contrasena = rs.getString("contrasena"),
                tiposrol_id = rs.getInt("tiposrol_id")
            )
        }.firstOrNull()

    }

    fun crearUsuario(usuario: Usuarios): Int {
        val sql = """
            INSERT INTO usuarios 
            (nombre_usuario, nombres, apellidos, tipo_documento_id, numero_documento, telefono, direccion, ciudad, email, contrasena, tiposrol_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

        return jdbcTemplate.update(
            sql,
            usuario.nombre_usuario,
            usuario.nombres,
            usuario.apellidos,
            usuario.tipo_documento_id,
            usuario.numero_documento,
            usuario.telefono,
            usuario.direccion,
            usuario.ciudad,
            usuario.email,
            usuario.contrasena,
            usuario.tiposrol_id
        )
    }
    fun actualizarUsuario(id: Int, usuario: Usuarios): Int {
        val sql = """
            UPDATE usuarios SET 
                nombre_usuario = ?, nombres = ?, apellidos = ?, 
                tipo_documento_id = ?, numero_documento = ?, telefono = ?, 
                direccion = ?, ciudad = ?, email = ?, contrasena = ?, tiposrol_id = ?
            WHERE id = ?
        """.trimIndent()

        return jdbcTemplate.update(
            sql,
            usuario.nombre_usuario,
            usuario.nombres,
            usuario.apellidos,
            usuario.tipo_documento_id,
            usuario.numero_documento,
            usuario.telefono,
            usuario.direccion,
            usuario.ciudad,
            usuario.email,
            usuario.contrasena,
            usuario.tiposrol_id,
            id
        )
    }
    fun eliminarUsuario(id: Int): Int {
        val sql = "DELETE FROM usuarios WHERE id = ?"
        return jdbcTemplate.update(sql, id)
    }
}
