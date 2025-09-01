package com.example.demo.despachos

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ServiceDespachos {



    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
    fun Listardespachos(): List<Despachos>{
        val sql="SELECT * FROM despachos"
        return jdbcTemplate.query(sql, {rs, _ ->
            Despachos(
                id = rs.getInt("id"),
                mercancias_id = rs.getInt("mercancias_id"),
                vehiculos_id = rs.getInt("vehiculos_id"),
                usuarios_id = rs.getInt("usuarios_id"),
                tipo_pago_id = rs.getInt("tipo_pago_id"),
                fecha_despacho = rs.getTimestamp("fecha_despacho").toLocalDateTime(),
                negociacion = rs.getDouble("negociacion"),
                anticipo = rs.getDouble("anticipo"),
                saldo = rs.getDouble("saldo"),
                observaciones_mer = rs.getString("observaciones_mer")
            )
        }
        )
    }


    fun Detalleddespachos(id: Int): Despachos? {
        val sql = "SELECT * FROM despachos WHERE id = ?"
        return jdbcTemplate.query(sql, arrayOf(id)) { rs, _ ->
            Despachos(
                id = rs.getInt("id"),
                mercancias_id = rs.getInt("mercancias_id"),
                vehiculos_id = rs.getInt("vehiculos_id"),
                usuarios_id = rs.getInt("usuarios_id"),
                tipo_pago_id = rs.getInt("tipo_pago_id"),
                fecha_despacho = rs.getTimestamp("fecha_despacho").toLocalDateTime(),
                negociacion = rs.getDouble("negociacion"),
                anticipo = rs.getDouble("anticipo"),
                saldo = rs.getDouble("saldo"),
                observaciones_mer = rs.getString("observaciones_mer")
            )
        }.firstOrNull()

    }
}