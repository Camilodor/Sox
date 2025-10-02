package com.example.Sox.entregas


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
                despachos_id = rs.getInt("despachos_id"),
                usuarios_id = rs.getInt("usuarios_id"),
                nombre_recibe = rs.getString("nombre_recibe"),
                numero_celular_recibe = rs.getString("numero_celular_recibe"),
                observaciones = rs.getString("observaciones"),
                fecha_entrega = rs.getString("fecha_entrega"),
                estado_entrega = rs.getString("estado_entrega") ?: "Pendiente",
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime().toString(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime().toString()
            )
        })
    }


    fun Crearentregas(entregas: Entregas): Int {
        val sql = """
            INSERT INTO entregas 
            (mercancias_id, despachos_id, usuarios_id, nombre_recibe, numero_celular_recibe, observaciones, fecha_entrega, estado_entrega)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()
        return jdbcTemplate.update (
            sql,
            entregas.mercancias_id,
            entregas.despachos_id,
            entregas.usuarios_id,
            entregas.nombre_recibe,
            entregas.numero_celular_recibe,
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
                despachos_id = rs.getInt("despachos_id"),
                usuarios_id = rs.getInt("usuarios_id"),
                nombre_recibe = rs.getString("nombre_recibe"),
                numero_celular_recibe = rs.getString("numero_celular_recibe"),
                observaciones = rs.getString("observaciones"),
                fecha_entrega = rs.getString("fecha_entrega"),
                estado_entrega = rs.getString("estado_entrega") ?: "Pendiente",
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime().toString(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime().toString()
            )
        }.firstOrNull()

    }

    fun Actualizarentrega(id: Int, entregas: Entregas): Int{
        val sql = """
            UPDATE entregas SET 
                mercancias_id = ?, despachos_id = ?, usuarios_id = ?, 
                  nombre_recibe = ?, numero_celular_recibe = ?, observaciones = ?, 
                 fecha_entrega = ?, estado_entrega = ?
            WHERE id = ?
        """.trimIndent()

        return jdbcTemplate.update (
            sql,
            entregas.mercancias_id,
            entregas.despachos_id,
            entregas.usuarios_id,
            entregas.nombre_recibe,
            entregas.numero_celular_recibe,
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