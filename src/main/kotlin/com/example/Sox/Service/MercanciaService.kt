package com.example.Sox.Service

import com.example.Sox.Modelos.Mercancia
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
                proveedor_id = rs.getInt("proveedor_id").takeIf { !rs.wasNull() },
                usuario_id = rs.getInt("usuario_id").takeIf { !rs.wasNull() },
                fecha_ingreso = rs.getDate("fecha_ingreso").toLocalDate(),
                num_remesa = rs.getString("num_remesa"),
                origen_mer = rs.getString("origen_mer"),
                destino_mer = rs.getString("destino_mer"),

                nom_remitente = rs.getString("nom_remitente"),
                doc_remitente = rs.getString("doc_remitente"),
                direccion_remitente = rs.getString("direccion_remitente"),
                cel_remitente = rs.getString("cel_remitente"),

                destin_nom = rs.getString("destin_nom"),
                destin_doc = rs.getString("destin_doc"),
                destin_direccion = rs.getString("destin_direccion"),
                destin_celular = rs.getString("destin_celular"),

                val_declarado = rs.getDouble("val_declarado"),
                val_flete = rs.getDouble("val_flete"),
                val_total = rs.getDouble("val_total"),
                peso = rs.getDouble("peso"),
                unidades = rs.getInt("unidades"),
                observaciones = rs.getString("observaciones"),

                tipopago_id = rs.getInt("tipopago_id").takeIf { !rs.wasNull() }
            )
        }
    }

    // Obtener mercancia por ID
    fun obtenerMercanciaPorId(id: Int): Mercancia? {
        val sql = "SELECT * FROM mercancias WHERE id = ?"
        return jdbcTemplate.query(sql, arrayOf(id)) { rs, _ ->
            Mercancia(
                id = rs.getInt("id"),
                proveedor_id = rs.getInt("proveedor_id").takeIf { !rs.wasNull() },
                usuario_id = rs.getInt("usuario_id").takeIf { !rs.wasNull() },
                fecha_ingreso = rs.getDate("fecha_ingreso").toLocalDate(),
                num_remesa = rs.getString("num_remesa"),
                origen_mer = rs.getString("origen_mer"),
                destino_mer = rs.getString("destino_mer"),

                nom_remitente = rs.getString("nom_remitente"),
                doc_remitente = rs.getString("doc_remitente"),
                direccion_remitente = rs.getString("direccion_remitente"),
                cel_remitente = rs.getString("cel_remitente"),

                destin_nom = rs.getString("destin_nom"),
                destin_doc = rs.getString("destin_doc"),
                destin_direccion = rs.getString("destin_direccion"),
                destin_celular = rs.getString("destin_celular"),

                val_declarado = rs.getDouble("val_declarado"),
                val_flete = rs.getDouble("val_flete"),
                val_total = rs.getDouble("val_total"),
                peso = rs.getDouble("peso"),
                unidades = rs.getInt("unidades"),
                observaciones = rs.getString("observaciones"),

                tipopago_id = rs.getInt("tipopago_id").takeIf { !rs.wasNull() }
            )
        }.firstOrNull()
    }

    // Crear nueva mercancia
    fun crearMercancia(mercancia: Mercancia): Int {
        val sql = """
            INSERT INTO mercancias (
                proveedor_id, usuario_id, fecha_ingreso, num_remesa, origen_mer, destino_mer,
                nom_remitente, doc_remitente, direccion_remitente, cel_remitente,
                destin_nom, destin_doc, destin_direccion, destin_celular,
                val_declarado, val_flete, val_total, peso, unidades, observaciones, tipopago_id
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

        return jdbcTemplate.update(
            sql,
            mercancia.proveedor_id,
            mercancia.usuario_id,
            Date.valueOf(mercancia.fecha_ingreso),
            mercancia.num_remesa,
            mercancia.origen_mer,
            mercancia.destino_mer,

            mercancia.nom_remitente,
            mercancia.doc_remitente,
            mercancia.direccion_remitente,
            mercancia.cel_remitente,

            mercancia.destin_nom,
            mercancia.destin_doc,
            mercancia.destin_direccion,
            mercancia.destin_celular,

            mercancia.val_declarado,
            mercancia.val_flete,
            mercancia.val_total,
            mercancia.peso,
            mercancia.unidades,
            mercancia.observaciones,
            mercancia.tipopago_id
        )
    }

    // Actualizar mercancia por ID
    fun actualizarMercancia(id: Int, mercancia: Mercancia): Int {
        val sql = """
            UPDATE mercancias SET
                proveedor_id = ?, usuario_id = ?, fecha_ingreso = ?, num_remesa = ?, origen_mer = ?, destino_mer = ?,
                nom_remitente = ?, doc_remitente = ?, direccion_remitente = ?, cel_remitente = ?,
                destin_nom = ?, destin_doc = ?, destin_direccion = ?, destin_celular = ?,
                val_declarado = ?, val_flete = ?, val_total = ?, peso = ?, unidades = ?, observaciones = ?, tipopago_id = ?
            WHERE id = ?
        """.trimIndent()

        return jdbcTemplate.update(
            sql,
            mercancia.proveedor_id,
            mercancia.usuario_id,
            Date.valueOf(mercancia.fecha_ingreso),
            mercancia.num_remesa,
            mercancia.origen_mer,
            mercancia.destino_mer,

            mercancia.nom_remitente,
            mercancia.doc_remitente,
            mercancia.direccion_remitente,
            mercancia.cel_remitente,

            mercancia.destin_nom,
            mercancia.destin_doc,
            mercancia.destin_direccion,
            mercancia.destin_celular,

            mercancia.val_declarado,
            mercancia.val_flete,
            mercancia.val_total,
            mercancia.peso,
            mercancia.unidades,
            mercancia.observaciones,
            mercancia.tipopago_id,
            id
        )
    }

    // Eliminar mercancia por ID
    fun eliminarMercancia(id: Int): Int {
        val sql = "DELETE FROM mercancias WHERE id = ?"
        return jdbcTemplate.update(sql, id)
    }
}
