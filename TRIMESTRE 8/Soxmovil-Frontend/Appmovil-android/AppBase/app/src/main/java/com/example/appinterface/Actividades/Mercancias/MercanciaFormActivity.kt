package com.example.appinterface.Actividades.Mercancias

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.Mercancias.ApiMercancias
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Modelos.Mercancias.Mercancias
import com.example.appinterface.R
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MercanciaFormActivity : AppCompatActivity() {

    private val api: ApiMercancias = RetrofitInstance.apiMercancias
    private var mercanciaExistente: Mercancias? = null

    // Referencias UI
    private lateinit var etProveedores: EditText
    private lateinit var etUsuariosM: EditText
    private lateinit var etFechaM: EditText
    private lateinit var etCelularMR: EditText
    private lateinit var etRemesa: EditText
    private lateinit var etOrigen: EditText
    private lateinit var etDestino: EditText
    private lateinit var etNombreR: EditText
    private lateinit var etDocumentoR: EditText
    private lateinit var etDireccionR: EditText
    private lateinit var etNombreMD: EditText
    private lateinit var etDocumentoD: EditText
    private lateinit var etDireccionD: EditText
    private lateinit var etCelularMD: EditText
    private lateinit var etValorD: EditText
    private lateinit var etValorF: EditText
    private lateinit var etValorT: EditText
    private lateinit var etPeso: EditText
    private lateinit var etUnidades: EditText
    private lateinit var etObservacionesM: EditText
    private lateinit var spinnerTipopago: Spinner
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mercancias_form)


        etProveedores = findViewById(R.id.etProveedores)
        etUsuariosM = findViewById(R.id.etUsuariosM)
        etFechaM = findViewById(R.id.etFechaM)
        etCelularMR = findViewById(R.id.etCelularMR)
        etRemesa = findViewById(R.id.etRemesa)
        etOrigen = findViewById(R.id.etOrigen)
        etDestino = findViewById(R.id.etDestino)
        etNombreR = findViewById(R.id.etNombreR)
        etDocumentoR = findViewById(R.id.etDocumentoR)
        etDireccionR = findViewById(R.id.etDireccionR)
        etNombreMD = findViewById(R.id.etNombreMD)
        etDocumentoD = findViewById(R.id.etDocumentoD)
        etDireccionD = findViewById(R.id.etDireccionD)
        etCelularMD = findViewById(R.id.etCelularMD)
        etValorD = findViewById(R.id.etValorD)
        etValorF = findViewById(R.id.etValorF)
        etValorT = findViewById(R.id.etValorT)
        etPeso = findViewById(R.id.etPeso)
        etUnidades = findViewById(R.id.etUnidades)
        etObservacionesM = findViewById(R.id.etObservacionesM)
        spinnerTipopago = findViewById(R.id.spinnerTipopago)
        btnGuardar = findViewById(R.id.btnGuardar)

        // llenar spinner de tipo de pago
        val tiposPago = listOf("Crédito", "Contraentrega", "Contado")
        spinnerTipopago.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposPago)

        // precargar si es edición
        mercanciaExistente = intent.getSerializableExtra("mercancia") as? Mercancias
        mercanciaExistente?.let {
            etProveedores.setText(it.proveedores_id.toString())
            etUsuariosM.setText(it.usuarios_id.toString())
            etFechaM.setText(it.fecha_ingreso)
            etCelularMR.setText(it.celular_remitente)
            etRemesa.setText(it.numero_remesa)
            etOrigen.setText(it.origen_mercancia)
            etDestino.setText(it.destino_mercancia)
            etNombreR.setText(it.nombre_remitente)
            etDocumentoR.setText(it.documento_remitente.toString())
            etDireccionR.setText(it.direccion_remitente)
            etNombreMD.setText(it.nombre_destinatario)
            etDocumentoD.setText(it.documento_destinatario.toString())
            etDireccionD.setText(it.direccion_destinatario)
            etCelularMD.setText(it.celular_destinatario)
            etValorD.setText(it.valor_declarado.toString())
            etValorF.setText(it.valor_flete.toString())
            etValorT.setText(it.valor_total.toString())
            etPeso.setText(it.peso.toString())
            etUnidades.setText(it.unidades.toString())
            etObservacionesM.setText(it.observaciones)
            if (it.tipo_pago_id > 0 && it.tipo_pago_id <= tiposPago.size) {
                spinnerTipopago.setSelection(it.tipo_pago_id - 1)
            }
        }

        // Guardar
        btnGuardar.setOnClickListener {
            if (etProveedores.text.isBlank() || etUsuariosM.text.isBlank() || etRemesa.text.isBlank()) {
                Toast.makeText(this, "Completa proveedor, usuario y remesa", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevaMercancia = Mercancias(
                id = mercanciaExistente?.id ?: 0,
                proveedores_id = etProveedores.text.toString().toInt(),
                usuarios_id = etUsuariosM.text.toString().toInt(),
                fecha_ingreso = etFechaM.text.toString(),
                celular_remitente = etCelularMR.text.toString(),
                numero_remesa = etRemesa.text.toString(),
                origen_mercancia = etOrigen.text.toString(),
                destino_mercancia = etDestino.text.toString(),
                nombre_remitente = etNombreR.text.toString(),
                documento_remitente = etDocumentoR.text.toString(),
                direccion_remitente = etDireccionR.text.toString(),
                nombre_destinatario = etNombreMD.text.toString(),
                documento_destinatario = etDocumentoD.text.toString(),
                direccion_destinatario = etDireccionD.text.toString(),
                celular_destinatario = etCelularMD.text.toString(),
                valor_declarado = etValorD.text.toString().toDoubleOrNull() ?: 0.0,
                valor_flete = etValorF.text.toString().toDoubleOrNull() ?: 0.0,
                valor_total = etValorT.text.toString().toDoubleOrNull() ?: 0.0,
                peso = etPeso.text.toString().toDoubleOrNull() ?: 0.0,
                unidades = etUnidades.text.toString().toIntOrNull() ?: 0,
                observaciones = etObservacionesM.text.toString(),
                tipo_pago_id = spinnerTipopago.selectedItemPosition + 1
            )

            if (mercanciaExistente == null) {
                Log.d("MercanciaForm", "Creando mercancia: ${nuevaMercancia.numero_remesa}")
                api.createMercancia(nuevaMercancia).enqueue(callbackConMensaje("Mercancía creada"))
            } else {
                Log.d("MercanciaForm", "Actualizando mercancia ID=${nuevaMercancia.id}")
                nuevaMercancia.id?.let { it1 ->
                    api.updateMercancia(it1, nuevaMercancia)
                        .enqueue(callbackConMensaje("Mercancía actualizada"))
                }
            }
        }
    }

    private fun callbackConMensaje(accion: String): Callback<ResponseBody> {
        return object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("MercanciaForm", "$accion exitosamente")
                    val bodyStr = response.body()?.string()
                    val json = try {
                        JSONObject(bodyStr ?: "{}")
                    } catch (e: Exception) {
                        JSONObject()
                    }
                    val msg = json.optString("message", accion)
                    Toast.makeText(this@MercanciaFormActivity, msg, Toast.LENGTH_SHORT).show()

                    setResult(RESULT_OK, Intent().apply {
                        putExtra("accion", accion)
                    })
                    finish()
                } else {
                    Log.e("MercanciaForm", "Error ${response.code()}")
                    Toast.makeText(
                        this@MercanciaFormActivity,
                        "Error ${response.code()}: ${response.errorBody()?.string()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("MercanciaForm", "Fallo red: ${t.message}")
                Toast.makeText(
                    this@MercanciaFormActivity,
                    "Error red: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
