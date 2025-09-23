package com.example.Sox.mercancias


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.Date

@Service
class MercanciaService {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    // Obtener todas las mercancias
    fun obtenerMercancias(): List<Mercancia> {
        val sql = "SELECT * FROM mercancias"
        return jdbcTemplate.query(sql) { rs, _ ->
            Mercancia(
                id = rs.getInt("id"),
                proveedores_id = rs.getInt("proveedores_id"),
                usuarios_id = rs.getInt("usuarios_id"),
                fecha_ingreso = rs.getDate("fecha_ingreso").toLocalDate(),
                numero_remesa = rs.getString("numero_remesa"),
                origen_mercancia = rs.getString("origen_mercancia"),
                destino_mercancia = rs.getString("destino_mercancia"),

                nombre_remitente = rs.getString("nombre_remitente"),
                documento_remitente = rs.getString("documento_remitente"),
                direccion_remitente = rs.getString("direccion_remitente"),
                celular_remitente = rs.getString("celular_remitente"),

                nombre_destinatario = rs.getString("nombre_destinatario"),
                documento_destinatario = rs.getString("documento_destinatario"),
                direccion_destinatario = rs.getString("direccion_destinatario"),
                celular_destinatario = rs.getString("celular_destinatario"),

                valor_declarado = rs.getDouble("valor_declarado"),
                valor_flete = rs.getDouble("valor_flete"),
                valor_total = rs.getDouble("valor_total"),
                peso = rs.getDouble("peso"),
                unidades = rs.getInt("unidades"),
                observaciones = rs.getString("observaciones"),

                tipo_pago_id = rs.getInt("tipo_pago_id")
            )
        }
    }

    // Obtener mercancia por ID
    fun obtenerMercanciaPorId(id: Int): Mercancia? {
        val sql = "SELECT * FROM mercancias WHERE id = ?"
        return jdbcTemplate.query(sql, arrayOf(id)) { rs, _ ->
            Mercancia(
                id = rs.getInt("id"),
                proveedores_id = rs.getInt("proveedores_id"),
                usuarios_id = rs.getInt("usuarios_id"),
                fecha_ingreso = rs.getDate("fecha_ingreso").toLocalDate(),
                numero_remesa = rs.getString("numero_remesa"),
                origen_mercancia = rs.getString("origen_mercancia"),
                destino_mercancia = rs.getString("destino_mercancia"),

                nombre_remitente = rs.getString("nombre_remitente"),
                documento_remitente = rs.getString("documento_remitente"),
                direccion_remitente = rs.getString("direccion_remitente"),
                celular_remitente = rs.getString("celular_remitente"),

                nombre_destinatario = rs.getString("nombre_destinatario"),
                documento_destinatario = rs.getString("documento_destinatario"),
                direccion_destinatario = rs.getString("direccion_destinatario"),
                celular_destinatario = rs.getString("celular_destinatario"),

                valor_declarado = rs.getDouble("valor_declarado"),
                valor_flete = rs.getDouble("valor_flete"),
                valor_total = rs.getDouble("valor_total"),
                peso = rs.getDouble("peso"),
                unidades = rs.getInt("unidades"),
                observaciones = rs.getString("observaciones"),

                tipo_pago_id = rs.getInt("tipo_pago_id")
            )
        }.firstOrNull()
    }

    // Crear nueva mercancia
    fun crearMercancia(mercancia: Mercancia): Int {
        val sql = """
            INSERT INTO mercancias (
                proveedores_id, usuarios_id, fecha_ingreso, numero_remesa, origen_mercancia, destino_mercancia,
                nombre_remitente, documento_remitente, direccion_remitente, celular_remitente,
                nombre_destinatario, documento_destinatario, direccion_destinatario, celular_destinatario,
                valor_declarado, valor_flete, valor_total, peso, unidades, observaciones, tipo_pago_id
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

        return jdbcTemplate.update(
            sql,
            mercancia.proveedores_id,
            mercancia.usuarios_id,
            Date.valueOf(mercancia.fecha_ingreso),
            mercancia.numero_remesa,
            mercancia.origen_mercancia,
            mercancia.destino_mercancia,

            mercancia.nombre_remitente,
            mercancia.documento_remitente,
            mercancia.direccion_remitente,
            mercancia.celular_remitente,

            mercancia.nombre_destinatario,
            mercancia.documento_destinatario,
            mercancia.direccion_destinatario,
            mercancia.celular_destinatario,

            mercancia.valor_declarado,
            mercancia.valor_flete,
            mercancia.valor_total,
            mercancia.peso,
            mercancia.unidades,
            mercancia.observaciones,
            mercancia.tipo_pago_id
        )
    }

    // Actualizar mercancia por ID
    fun actualizarMercancia(id: Int, mercancia: Mercancia): Int {
        val sql = """
            UPDATE mercancias SET
                proveedores_id = ?, usuarios_id = ?, fecha_ingreso = ?, numero_remesa = ?, origen_mercancia = ?, destino_mercancia = ?,
                nombre_remitente = ?, documento_remitente = ?, direccion_remitente = ?, celular_remitente = ?,
                nombre_destinatario = ?, documento_destinatario = ?, direccion_destinatario = ?, celular_destinatario = ?,
                valor_declarado = ?, valor_flete = ?, valor_total = ?, peso = ?, unidades = ?, observaciones = ?, tipo_pago_id = ?
            WHERE id = ?
        """.trimIndent()

        return jdbcTemplate.update(
            sql,
            mercancia.proveedores_id,
            mercancia.usuarios_id,
            Date.valueOf(mercancia.fecha_ingreso),
            mercancia.numero_remesa,
            mercancia.origen_mercancia,
            mercancia.destino_mercancia,

            mercancia.nombre_remitente,
            mercancia.documento_remitente,
            mercancia.direccion_remitente,
            mercancia.celular_remitente,

            mercancia.nombre_destinatario,
            mercancia.documento_destinatario,
            mercancia.direccion_destinatario,
            mercancia.celular_destinatario,

            mercancia.valor_declarado,
            mercancia.valor_flete,
            mercancia.valor_total,
            mercancia.peso,
            mercancia.unidades,
            mercancia.observaciones,
            mercancia.tipo_pago_id,
            id
        )
    }

    // Eliminar mercancia por ID
    fun eliminarMercancia(id: Int): Int {
        val sql = "DELETE FROM mercancias WHERE id = ?"
        return jdbcTemplate.update(sql, id)
    }
}
