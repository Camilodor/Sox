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
                estado = rs.getString("estado"),
                observaciones = rs.getString("observaciones"),
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
                estado = rs.getString("estado"),
                observaciones = rs.getString("observaciones"),
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime()
            )
        }.firstOrNull()

    }
}