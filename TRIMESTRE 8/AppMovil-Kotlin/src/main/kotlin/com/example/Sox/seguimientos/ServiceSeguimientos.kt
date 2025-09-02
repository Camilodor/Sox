package com.example.Sox.seguimientos


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ServiceSeguimientos {


    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
    fun ListarSeguimientos(): List<Seguimientos>{
        val sql="SELECT * FROM seguimientos"
        return jdbcTemplate.query(sql, {rs, _ ->
            Seguimientos(

                id = rs.getInt("id"),
                mercancias_id = rs.getInt("mercancias_id"),
                despacho_id = rs.getInt("despacho_id"),
                entrega_id = rs.getInt("entrega_id"),
                devolucion_id = rs.getInt("devolucion_id"),
                usuario_id = rs.getInt("usuario_id"),
                evento = rs.getString("evento"),
                estado = rs.getString("estado"),
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime()
            )
        })
    }


    fun Detalleseguimientos(id: Int): Seguimientos? {
        val sql = "SELECT * FROM seguimientos WHERE id = ?"
        return jdbcTemplate.query(sql, arrayOf(id)) { rs, _ ->
            Seguimientos(
                id = rs.getInt("id"),
                mercancias_id = rs.getInt("mercancias_id"),
                despacho_id = rs.getInt("despacho_id"),
                entrega_id = rs.getInt("entrega_id"),
                devolucion_id = rs.getInt("devolucion_id"),
                usuario_id = rs.getInt("usuario_id"),
                evento = rs.getString("evento"),
                estado = rs.getString("estado"),
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime()
            )
        }.firstOrNull()

    }
}