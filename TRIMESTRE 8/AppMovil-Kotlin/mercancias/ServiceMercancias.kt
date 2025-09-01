package com.example.demo.mercancias

import com.example.demo.mercancias.Mercancias
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ServiceMercancias {



    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
    fun listarmercancias(): List<Mercancias> {
        val sql = "SELECT * FROM mercancias"
        return jdbcTemplate.query(sql) { rs, _ ->
            Mercancias(
                id = rs.getInt("id"),
                proveedor_id = rs.getInt("proveedor_id"),
                usuario_id = rs.getInt("usuario_id"),
                fecha_ingreso = rs.getTimestamp("fecha_ingreso")?.toLocalDateTime(),
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
                tipopago_id = rs.getInt("tipopago_id"),
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime()
            )
        }
    }



    fun Detallemercancia(id: Int): Mercancias? {
        val sql = "SELECT * FROM mercancias WHERE id = ?"
        return jdbcTemplate.query(sql, arrayOf(id)) { rs, _ ->
            Mercancias(
                id = rs.getInt("id"),
                proveedor_id = rs.getInt("proveedor_id"),
                usuario_id = rs.getInt("usuario_id"),
                fecha_ingreso = rs.getTimestamp("fecha_ingreso")?.toLocalDateTime(),
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
                tipopago_id = rs.getInt("tipopago_id"),
                createdAt = rs.getTimestamp("created_at")?.toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime()
            )
        }.firstOrNull()

    }
}