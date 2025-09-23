package com.example.Sox.devoluciones

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ServiceDevoluciones {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    fun listarDevoluciones(): List<Devoluciones> {
        val sql = "SELECT * FROM devoluciones"
        return jdbcTemplate.query(sql, { rs, _ ->
            Devoluciones(
                id = rs.getInt("id"),
                mercancias_id = rs.getInt("mercancias_id"),
                despachos_id = rs.getInt("despachos_id"),
                usuarios_id = rs.getInt("usuarios_id"),

                fecha_devolucion = rs.getTimestamp("fecha_devolucion").toLocalDateTime(),
                motivo_devolucion = rs.getString("motivo_devolucion"),
                estado_devolucion = rs.getString("estado_devolucion"),
                observaciones = rs.getString("observaciones"),
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime()
            )
        })
    }


    fun Creardevoluciones(devolucion: Devoluciones): Int {
        val sql = """
            INSERT INTO devoluciones
            (mercancias_id, despachos_id, usuarios_id , fecha_devolucion,motivo_devolucion,estado_devolucion, observaciones)
            VALUES (?, ?, ?, ?, ?, ?, ? )
        """.trimIndent()

        return jdbcTemplate.update(
            sql, devolucion.mercancias_id,
            devolucion.despachos_id,
            devolucion.usuarios_id,
            devolucion.fecha_devolucion,
            devolucion.motivo_devolucion,
            devolucion.estado_devolucion,
            devolucion.observaciones
        )
    }


    fun Detalledevolucion(id: Int): Devoluciones? {
        val sql = "SELECT * FROM devoluciones WHERE id = ?"
        return jdbcTemplate.query(sql, arrayOf(id)) { rs, _ ->
            Devoluciones(
                id = rs.getInt("id"),
                mercancias_id = rs.getInt("mercancias_id"),
                despachos_id = rs.getInt("despachos_id"),
                usuarios_id = rs.getInt("usuarios_id"),

                fecha_devolucion = rs.getTimestamp("fecha_devolucion").toLocalDateTime(),
                motivo_devolucion = rs.getString("motivo_devolucion"),
                estado_devolucion = rs.getString("estado_devolucion"),
                observaciones = rs.getString("observaciones"),
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime()
            )
        }.firstOrNull()

    }


    fun actualizarDevolucion(id: Int, devolucion: Devoluciones): Int{
        val sql= """
            UPDATE devoluciones SET 
                mercancias_id = ?, despachos_id = ?,  usuarios_id = ?,
                  fecha_devolucion= ?, motivo_devolucion = ?, estado_devolucion = ?, observaciones = ?
            WHERE id = ?
        """.trimIndent()


        return jdbcTemplate.update (
            sql, devolucion.mercancias_id,
            devolucion.despachos_id,
            devolucion.usuarios_id,
            devolucion.fecha_devolucion,
            devolucion.motivo_devolucion,
            devolucion.estado_devolucion,
            devolucion.observaciones,
            id
        )
    }

    fun EliminarDevolucion(id: Int): Int {
        val sql = "DELETE FROM devoluciones WHERE id = ?"
        return jdbcTemplate.update(sql, id)
    }
}