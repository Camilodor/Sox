package com.example.appinterface.Actividades.Entregas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.Entregas.ApiEntregas
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import com.example.appinterface.Modelos.Entregas.Entregas
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EntregaFormActivity : AppCompatActivity() {

    private val api: ApiEntregas = RetrofitInstance.apiEntregas
    private var entregaExistente: Entregas? = null

    private lateinit var etMercanciaE: EditText
    private lateinit var etDespachosE: EditText
    private lateinit var etUsuariosE: EditText
    private lateinit var etNombres: EditText
    private lateinit var etCelularE: EditText
    private lateinit var etFecha: EditText
    private lateinit var etEstado: EditText
    private lateinit var etObservaciones: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrega_form)
        
        etMercanciaE = findViewById(R.id.etMercanciaE)
        etDespachosE = findViewById(R.id.etDespachosE)
        etUsuariosE = findViewById(R.id.etUsuariosE)
        etNombres = findViewById(R.id.etNombres)
        etCelularE = findViewById(R.id.etCelularE)
        etFecha = findViewById(R.id.etFecha)
        etEstado = findViewById(R.id.etEstado)
        etObservaciones = findViewById(R.id.etObservaciones)
        btnGuardar = findViewById(R.id.btnGuardar)

        etFecha.setOnClickListener {
            val c = java.util.Calendar.getInstance()
            val year = c.get(java.util.Calendar.YEAR)
            val month = c.get(java.util.Calendar.MONTH)
            val day = c.get(java.util.Calendar.DAY_OF_MONTH)

            android.app.DatePickerDialog(this, { _, y, m, d ->
                // Formato ISO-8601 con hora incluida para LocalDateTime
                val formattedDateTime = String.format("%04d-%02d-%02dT00:00:00", y, m + 1, d)
                etFecha.setText(formattedDateTime)
            }, year, month, day).show()
        }


        entregaExistente = intent.getSerializableExtra("entrega") as? Entregas
        entregaExistente?.let {
            etMercanciaE.setText(it.mercancias_id.toString())
            etDespachosE.setText(it.despachos_id.toString())
            etUsuariosE.setText(it.usuarios_id.toString())
            etNombres.setText(it.nombre_recibe)
            etCelularE.setText(it.numero_celular_recibe)
            etFecha.setText(it.fecha_entrega)
            etEstado.setText(it.estado_entrega)
            etObservaciones.setText(it.observaciones)



        }

        // Guardar
        btnGuardar.setOnClickListener {
            if (etMercanciaE.text.isBlank() || etDespachosE.text.isBlank() || etUsuariosE.text.isBlank()) {
                Toast.makeText(this, "Completa la entrega, mercancias, despachos y usuarios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoEntrega = Entregas(
                id = entregaExistente?.id ?: 0,
                mercancias_id = etMercanciaE.text.toString().toInt(),
                despachos_id = etDespachosE.text.toString().toInt(),
                usuarios_id = etUsuariosE.text.toString().toInt(),
                nombre_recibe = etNombres.text.toString(),
                numero_celular_recibe = etCelularE.text.toString(),
                fecha_entrega = etFecha.text.toString(),
                estado_entrega = etEstado.text.toString(),
                observaciones = etObservaciones.text.toString(),

            )
            if (entregaExistente == null) {
                api.createEntrega(nuevoEntrega).enqueue(callbackConMensaje("Entrega creada"))
            } else {
                nuevoEntrega.id?.let {
                    api.updateEntrega(it, nuevoEntrega).enqueue(callbackConMensaje("Entrega actualizada"))
                }
            }

        }
    }

    private fun callbackConMensaje(accion: String): Callback<ResponseBody> {
        return object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("EntregaForm", "$accion exitosamente")
                    val bodyStr = response.body()?.string()
                    val json = try {
                        JSONObject(bodyStr ?: "{}")
                    } catch (e: Exception) {
                        JSONObject()
                    }
                    val msg = json.optString("message", accion)
                    Toast.makeText(this@EntregaFormActivity, msg, Toast.LENGTH_SHORT).show()


                    setResult(RESULT_OK, Intent().apply {
                        putExtra("accion", accion)
                    })
                    finish()
                } else {
                    Log.e("EntregaForm", "Error ${response.code()}")
                    Toast.makeText(
                        this@EntregaFormActivity,
                        "Error ${response.code()}: ${response.errorBody()?.string()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("EntregaForm", "Fallo red: ${t.message}")
                Toast.makeText(
                    this@EntregaFormActivity,
                    "Error red: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
