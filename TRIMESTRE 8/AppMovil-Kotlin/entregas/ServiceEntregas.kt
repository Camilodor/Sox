package com.example.demo.entregas

import com.example.demo.entregas.Entregas
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ServiceEntregas {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    fun Listarentregas(): List<Entregas> {
        val sql = "SELECT * FROM entregas"
        return jdbcTemplate.query(sql, { rs, _ ->
            Entregas(
                id = rs.getInt("id"),
                mercancias_id = rs.getInt("mercancias_id"),
                despacho_id = rs.getInt("despacho_id"),
                usuario_id = rs.getInt("usuario_id"),
                nombre_recibe = rs.getString("nombre_recibe"),
                num_celular_recibe = rs.getString("num_celular_recibe"),
                observaciones = rs.getString("observaciones"),
                fecha_entrega = rs.getTimestamp("fecha_entrega").toLocalDateTime(),
                estado_entrega = rs.getString("estado_entrega") ?: "Pendiente",
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime().toString(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime().toString()
            )
        })
    }


    fun Crearentregas(entregas: Entregas): Int {
        val sql = """
            INSERT INTO entregas 
            (mercancias_id,despacho_id , usuario_id, nombre_recibe,num_celular_recibe, observaciones, fecha_entrega, estado_entrega)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()
        return jdbcTemplate.update (
            sql,
            entregas.mercancias_id,
            entregas.despacho_id,
            entregas.usuario_id,
            entregas.nombre_recibe,
            entregas.num_celular_recibe,
            entregas.observaciones,
            entregas.fecha_entrega,
            entregas.estado_entrega

        )
    }


    fun Detalleentrega(id: Int): Entregas? {
        val sql = "SELECT * FROM entregas WHERE id = ?"
        return jdbcTemplate.query(sql, arrayOf(id)) { rs, _ ->
            Entregas(
                id = rs.getInt("id"),
                mercancias_id = rs.getInt("mercancias_id"),
                despacho_id = rs.getInt("despacho_id"),
                usuario_id = rs.getInt("usuario_id"),
                nombre_recibe = rs.getString("nombre_recibe"),
                num_celular_recibe = rs.getString("num_celular_recibe"),
                observaciones = rs.getString("observaciones"),
                fecha_entrega = rs.getTimestamp("fecha_entrega").toLocalDateTime(),
                estado_entrega = rs.getString("estado_entrega") ?: "Pendiente",
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime().toString(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime().toString()
            )
        }.firstOrNull()

    }

    fun Actualizarentrega(id: Int, entregas: Entregas): Int{
        val sql = """
            UPDATE entregas SET 
                mercancias_id = ?, despacho_id = ?, usuario_id = ?, 
                  nombre_recibe = ?, num_celular_recibe = ?, observaciones = ?, 
                 fecha_entrega = ?, estado_entrega = ?
            WHERE id = ?
        """.trimIndent()

        return jdbcTemplate.update (
            sql,
            entregas.mercancias_id,
            entregas.despacho_id,
            entregas.usuario_id,
            entregas.nombre_recibe,
            entregas.num_celular_recibe,
            entregas.observaciones,
            entregas.fecha_entrega,
            entregas.estado_entrega,
            id
        )
    }
    fun Eliminarentrega(id: Int): Int {
        val sql = "DELETE FROM entregas WHERE id = ?"
        return jdbcTemplate.update(sql, id)
    }

}