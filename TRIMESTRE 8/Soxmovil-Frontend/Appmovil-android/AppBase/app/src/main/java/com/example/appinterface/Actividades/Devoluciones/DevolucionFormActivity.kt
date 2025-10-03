package com.example.appinterface.Actividades.Devoluciones

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.Devoluciones.ApiDevoluciones
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Modelos.Devoluciones.Devoluciones
import com.example.appinterface.R
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DevolucionFormActivity : AppCompatActivity() {

    private val api: ApiDevoluciones = RetrofitInstance.apiDevoluciones
    private var devolucionExistente: Devoluciones? = null

    private lateinit var etMercanciaD: EditText
    private lateinit var etDespachosD: EditText
    private lateinit var etUsuariosD: EditText
    private lateinit var etFechaD: EditText
    private lateinit var etMotivo: EditText
    private lateinit var etEstadoD: EditText
    private lateinit var etObservacionesD: EditText
    private lateinit var btnGuardar: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devoluciones_form)


        etMercanciaD = findViewById(R.id.etMercanciaD)
        etDespachosD = findViewById(R.id.etDespachosD)
        etUsuariosD = findViewById(R.id.etUsuariosD)
        etFechaD = findViewById(R.id.etFechaD)
        etMotivo = findViewById(R.id.etMotivo)
        etEstadoD = findViewById(R.id.etEstadoD)
        etObservacionesD = findViewById(R.id.etObservacionesD)
        btnGuardar = findViewById(R.id.btnGuardar)

        etFechaD.setOnClickListener {
            val c = java.util.Calendar.getInstance()
            val year = c.get(java.util.Calendar.YEAR)
            val month = c.get(java.util.Calendar.MONTH)
            val day = c.get(java.util.Calendar.DAY_OF_MONTH)

            android.app.DatePickerDialog(this, { _, y, m, d ->
                // Formato ISO-8601 con hora incluida para LocalDateTime
                val formattedDateTime = String.format("%04d-%02d-%02dT00:00:00", y, m + 1, d)
                etFechaD.setText(formattedDateTime)
            }, year, month, day).show()
        }



        devolucionExistente = intent.getSerializableExtra("devolucion") as? Devoluciones
        devolucionExistente?.let {
            etMercanciaD.setText(it.mercancias_id.toString())
            etDespachosD.setText(it.despachos_id.toString())
            etUsuariosD.setText(it.usuarios_id.toString())
            etFechaD.setText(it.fecha_devolucion)
            etMotivo.setText(it.motivo_devolucion)
            etEstadoD.setText(it.estado_devolucion)
            etObservacionesD.setText(it.observaciones)

        }


        btnGuardar.setOnClickListener {
            if (etMercanciaD.text.isBlank() || etDespachosD.text.isBlank() || etUsuariosD.text.isBlank()) {
                Toast.makeText(this, "Completa mercancias, despachos y usuarios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoDevolucion = Devoluciones(
                id = devolucionExistente?.id ?: 0,
                mercancias_id = etMercanciaD.text.toString().toInt(),
                despachos_id = etDespachosD.text.toString().toInt(),
                usuarios_id = etUsuariosD.text.toString().toInt(),
                fecha_devolucion = etFechaD.text.toString(),
                motivo_devolucion = etMotivo.text.toString(),
                estado_devolucion = etEstadoD.text.toString(),
                observaciones = etObservacionesD?.text.toString(),
            )

            if (devolucionExistente == null) {
                Log.d("DevolucionForm", "Creando devolucion: ${nuevoDevolucion.mercancias_id}")
                api.createDevolucion(nuevoDevolucion).enqueue(callbackConMensaje("Devolucion creada"))
            } else {
                Log.d("DevolucionForm", "Actualizando devolucion ID=${nuevoDevolucion.id}")
                nuevoDevolucion.id?.let { it1 ->
                    api.updateDevolucion(it1, nuevoDevolucion)
                        .enqueue(callbackConMensaje("Devolucion actualizado"))
                }
            }
        }
    }

    private fun callbackConMensaje(accion: String): Callback<ResponseBody> {
        return object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("DevolucionForm", "$accion exitosamente")
                    val bodyStr = response.body()?.string()
                    val json = try {
                        JSONObject(bodyStr ?: "{}")
                    } catch (e: Exception) {
                        JSONObject()
                    }
                    val msg = json.optString("message", accion)
                    Toast.makeText(this@DevolucionFormActivity, msg, Toast.LENGTH_SHORT).show()


                    setResult(RESULT_OK, Intent().apply {
                        putExtra("accion", accion)
                    })
                    finish()
                } else {
                    Log.e("DevolucionForm", "Error ${response.code()}")
                    Toast.makeText(
                        this@DevolucionFormActivity,
                        "Error ${response.code()}: ${response.errorBody()?.string()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("DevolucionForm", "Fallo red: ${t.message}")
                Toast.makeText(
                    this@DevolucionFormActivity,
                    "Error red: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
