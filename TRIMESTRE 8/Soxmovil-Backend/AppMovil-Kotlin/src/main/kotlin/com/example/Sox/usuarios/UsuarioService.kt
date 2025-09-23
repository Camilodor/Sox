package com.example.Sox.usuarios.UsuarioService



import com.example.Sox.usuarios.Usuarios
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service


@Service
class UsuarioService {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
    fun obtenerUsuarios(): List<Usuarios> {
        val sql = "SELECT * FROM users"
        return jdbcTemplate.query(sql) { rs, _ ->
            Usuarios(
                id = rs.getInt("id"),
                nombre_usuario = rs.getString("nombre_usuario"),
                nombres = rs.getString("nombres"),
                apellidos = rs.getString("apellidos"),
                tipo_documento_id = rs.getInt("tipo_documento_id"),
                numero_documento = rs.getLong("numero_documento"),
                celular = rs.getString("celular"),
                direccion = rs.getString("direccion"),
                ciudad = rs.getString("ciudad"),
                email = rs.getString("email"),
                contrasena = rs.getString("contrasena"),
                tipo_rol_id = rs.getInt("tipo_rol_id")
            )
        }
    }

    fun obtenerUsuarioPorId(id: Int): Usuarios? {
        val sql = "SELECT * FROM users WHERE id = ?"
        return jdbcTemplate.query(sql, arrayOf(id)) { rs, _ ->
            Usuarios(
                id = rs.getInt("id"),
                nombre_usuario = rs.getString("nombre_usuario"),
                nombres = rs.getString("nombres"),
                apellidos = rs.getString("apellidos"),
                tipo_documento_id = rs.getInt("tipo_documento_id"),
                numero_documento = rs.getLong("numero_documento"),
                celular = rs.getString("celular"),
                direccion = rs.getString("direccion"),
                ciudad = rs.getString("ciudad"),
                email = rs.getString("email"),
                contrasena = rs.getString("contrasena"),
                tipo_rol_id = rs.getInt("tipo_rol_id")
            )
        }.firstOrNull()

    }

    fun crearUsuario(usuario: Usuarios): Int {
        val sql = """
            INSERT INTO users
            (nombre_usuario, nombres, apellidos, tipo_documento_id, numero_documento, celular, direccion, ciudad, email, contrasena, tipo_rol_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

        return jdbcTemplate.update(
            sql,
            usuario.nombre_usuario,
            usuario.nombres,
            usuario.apellidos,
            usuario.tipo_documento_id,
            usuario.numero_documento,
            usuario.celular,
            usuario.direccion,
            usuario.ciudad,
            usuario.email,
            usuario.contrasena,
            usuario.tipo_rol_id
        )
    }
    fun actualizarUsuario(id: Int, usuario: Usuarios): Int {
        val sql = """
            UPDATE users SET 
                nombre_usuario = ?, nombres = ?, apellidos = ?, 
                tipo_documento_id = ?, numero_documento = ?, celular = ?, 
                direccion = ?, ciudad = ?, email = ?, contrasena = ?, tipo_rol_id = ?
            WHERE id = ?
        """.trimIndent()

        return jdbcTemplate.update(
            sql,
            usuario.nombre_usuario,
            usuario.nombres,
            usuario.apellidos,
            usuario.tipo_documento_id,
            usuario.numero_documento,
            usuario.celular,
            usuario.direccion,
            usuario.ciudad,
            usuario.email,
            usuario.contrasena,
            usuario.tipo_rol_id,
            id
        )
    }
    fun eliminarUsuario(id: Int): Int {
        val sql = "DELETE FROM users WHERE id = ?"
        return jdbcTemplate.update(sql, id)
    }
}
