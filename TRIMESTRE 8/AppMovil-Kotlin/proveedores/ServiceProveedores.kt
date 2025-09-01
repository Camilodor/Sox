package com.example.demo.proveedores

import com.example.demo.proveedores.Provedores
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ServiceProveedores {



    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
    fun Listarproveedores(): List<Provedores>{
        val sql="SELECT * FROM proveedores"
        return jdbcTemplate.query(sql, {rs, _ ->
            Provedores(
                id = rs.getInt("id"),
                nombre = rs.getString("nombre"),
                descripcion = rs.getString("descripcion"),
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime()


            )
        })
    }
    fun DetalleProveedor(id: Int): Provedores? {
        val sql = "SELECT * FROM proveedores WHERE id = ?"
        return jdbcTemplate.query(sql, arrayOf(id)) { rs, _ ->
            Provedores(
                id = rs.getInt("id"),
                nombre = rs.getString("nombre"),
                descripcion = rs.getString("descripcion"),
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime()
            )
        }.firstOrNull()

    }
}