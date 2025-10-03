package com.example.appinterface.Actividades.Entregas

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.Entregas.ApiEntregas
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import com.example.appinterface.Modelos.Entregas.Entregas
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EntregaDetalleActivity : AppCompatActivity() {

    private val api: ApiEntregas = RetrofitInstance.apiEntregas
    private var entrega: Entregas? = null
    private var modo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrega_detalle)


        val etIdEntrega: EditText = findViewById(R.id.etIdEntrega)
        val etMercanciaE: EditText = findViewById(R.id.etMercanciaE)
        val etDespachosE: EditText = findViewById(R.id.etDespachosE)
        val etUsuariosE: EditText = findViewById(R.id.etUsuariosE)
        val etNombres: EditText = findViewById(R.id.etNombres)
        val etCelularE: EditText = findViewById(R.id.etCelularE)
        val etFecha: EditText = findViewById(R.id.etFecha)
        val etEstado: EditText = findViewById(R.id.etEstado)
        val etObservaciones: EditText = findViewById(R.id.etObservaciones)
        val btnGuardar: Button = findViewById(R.id.btnGuardar)
        val btnCancelar: Button = findViewById(R.id.btnCancelar)


        entrega = intent.getSerializableExtra("entrega") as? Entregas
        modo = intent.getStringExtra("modo")

        entrega?.let {
            etIdEntrega.setText(it.id.toString())
            etMercanciaE.setText(it.mercancias_id.toString())
            etDespachosE.setText(it.despachos_id.toString())
            etUsuariosE.setText(it.usuarios_id.toString())
            etNombres.setText(it.nombre_recibe)
            etCelularE.setText(it.numero_celular_recibe)
            etFecha.setText(it.fecha_entrega)
            etEstado.setText(it.estado_entrega)
            etObservaciones.setText(it.observaciones)
        }

        if (modo == "CONSULTA") {
            etMercanciaE.isEnabled = false
            etDespachosE.isEnabled = false
            etUsuariosE.isEnabled = false
            etNombres.isEnabled = false
            etCelularE.isEnabled = false
            etFecha.isEnabled = false
            etEstado.isEnabled = false
            etObservaciones.isEnabled = false
            btnGuardar.isEnabled = false
        }


        btnGuardar.setOnClickListener {
            val entregaEditada = Entregas(
                id = entrega?.id ?: 0,
                mercancias_id = etMercanciaE.text.toString().toInt(),
                despachos_id = etDespachosE.text.toString().toInt(),
                usuarios_id = etUsuariosE.text.toString().toInt(),
                nombre_recibe = etNombres.text.toString(),
                numero_celular_recibe = etCelularE.text.toString(),
                fecha_entrega = etFecha.text.toString(),
                estado_entrega = etEstado.text.toString(),
                observaciones = etObservaciones.text.toString(),
            )

            Log.d("EntregaDetalle", "Intentando actualizar entrega ID=${entregaEditada.id}")

            api.updateEntrega(entregaEditada.id, entregaEditada)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("EntregaDetalle", "Entrega actualizada correctamente")
                            Toast.makeText(
                                this@EntregaDetalleActivity,
                                "Entrega actualizada correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Log.e("EntregaDetalle", "Error al actualizar: ${response.code()}")
                            Toast.makeText(
                                this@EntregaDetalleActivity,
                                "Error al actualizar",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("EntregaDetalle", "Fallo red: ${t.message}")
                        Toast.makeText(
                            this@EntregaDetalleActivity,
                            "Error: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }

        btnCancelar.setOnClickListener {
            Log.d("EntregaDetalle", "Acción cancelar")
            finish()
        }
    }
}
