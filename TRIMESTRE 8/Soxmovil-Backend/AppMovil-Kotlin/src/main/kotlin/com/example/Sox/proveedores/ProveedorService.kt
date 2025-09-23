package com.example.Sox.proveedores


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ProveedorService {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    // Obtener todos los proveedores
    fun obtenerProveedores(): List<Proveedor> {
        val sql = "SELECT * FROM proveedores"
        return jdbcTemplate.query(sql) { rs, _ ->
            Proveedor(
                id = rs.getInt("id"),
                usuarios_id = rs.getInt("usuarios_id"),
                nombre = rs.getString("nombre"),
                descripcion = rs.getString("descripcion")
            )
        }
    }

    // Obtener proveedor por ID
    fun obtenerProveedorPorId(id: Int): Proveedor? {
        val sql = "SELECT * FROM proveedores WHERE id = ?"
        return jdbcTemplate.query(sql, arrayOf(id)) { rs, _ ->
            Proveedor(
                id = rs.getInt("id"),
                usuarios_id = rs.getInt("usuarios_id"),
                nombre = rs.getString("nombre"),
                descripcion = rs.getString("descripcion")
            )
        }.firstOrNull()
    }

    // Crear nuevo proveedor
    fun crearProveedor(proveedor: Proveedor): Int {
        val sql = """
            INSERT INTO proveedores (usuarios_id, nombre, descripcion)
            VALUES (?, ?, ?)
        """.trimIndent()

        return jdbcTemplate.update(
            sql,
            proveedor.usuarios_id,
            proveedor.nombre,
            proveedor.descripcion
        )
    }

    // Actualizar proveedor por ID
    fun actualizarProveedor(id: Int, proveedor: Proveedor): Int {
        val sql = """
            UPDATE proveedores SET 
                usuarios_id = ?,
                nombre = ?, 
                descripcion = ?
            WHERE id = ?
        """.trimIndent()

        return jdbcTemplate.update(
            sql,
            proveedor.usuarios_id,
            proveedor.nombre,
            proveedor.descripcion,
            id
        )
    }

    // Eliminar proveedor por ID
    fun eliminarProveedor(id: Int): Int {
        val sql = "DELETE FROM proveedores WHERE id = ?"
        return jdbcTemplate.update(sql, id)
    }
}
