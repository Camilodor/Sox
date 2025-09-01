package com.example.demo.tipopago

import com.example.demo.tipopago.TipoPago
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ServiceTipoPago {


    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
    fun listartipospago(): List<TipoPago>{
        val sql="SELECT * FROM tipospago"
        return jdbcTemplate.query(sql,  {rs, _ ->
            TipoPago(
                id = rs.getInt("id"),
                nombre = rs.getString("nombre"),
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime()
            )
        })
    }


    fun Detalletipospago(id: Int): TipoPago? {
        val sql = "SELECT * FROM proveedores WHERE id = ?"
        return jdbcTemplate.query(sql, arrayOf(id)) { rs, _ ->
            TipoPago(
                id = rs.getInt("id"),
                nombre = rs.getString("nombre"),
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime()
            )
        }.firstOrNull()

    }
}