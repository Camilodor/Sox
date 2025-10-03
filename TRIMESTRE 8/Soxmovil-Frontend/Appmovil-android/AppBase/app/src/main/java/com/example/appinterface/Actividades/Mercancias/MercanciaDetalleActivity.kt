package com.example.appinterface.Actividades.Mercancias

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Api.Mercancias.ApiMercancias
import com.example.appinterface.Modelos.Mercancias.Mercancias
import com.example.appinterface.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MercanciaDetalleActivity : AppCompatActivity() {

    private val api: ApiMercancias = RetrofitInstance.apiMercancias
    private var mercancia: Mercancias? = null
    private var modo: String? = null // "CONSULTA" o "EDICION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mercancias_detalle)

        // Referencias UI
        val etIdMercancia: EditText = findViewById(R.id.etIdMercancia)
        val etProveedores: EditText = findViewById(R.id.etProveedores)
        val etUsuariosM: EditText = findViewById(R.id.etUsuariosM)
        val etFechaM: EditText = findViewById(R.id.etFechaM)
        val etRemesa: EditText = findViewById(R.id.etRemesa)
        val etOrigen: EditText = findViewById(R.id.etOrigen)
        val etDestino: EditText = findViewById(R.id.etDestino)
        val etNombreR: EditText = findViewById(R.id.etNombreR)
        val etDocumentoR: EditText = findViewById(R.id.etDocumentoR)
        val etDireccionR: EditText = findViewById(R.id.etDireccionR)
        val etCelularMR: EditText = findViewById(R.id.etCelularMR)
        val etNombreMD: EditText = findViewById(R.id.etNombreMD)
        val etDocumentoD: EditText = findViewById(R.id.etDocumentoD)
        val etDireccionD: EditText = findViewById(R.id.etDireccionD)
        val etCelularMD: EditText = findViewById(R.id.etCelularMD)
        val etValorD: EditText = findViewById(R.id.etValorD)
        val etValorF: EditText = findViewById(R.id.etValorF)
        val etValorT: EditText = findViewById(R.id.etValorT)
        val etPeso: EditText = findViewById(R.id.etPeso)
        val etUnidades: EditText = findViewById(R.id.etUnidades)
        val etObservacionesM: EditText = findViewById(R.id.etObservacionesM)
        val btnCancelar: Button = findViewById(R.id.btnCancelar)

        // Recibir mercancia y modo desde el intent
        mercancia = intent.getSerializableExtra("mercancia") as? Mercancias
        modo = intent.getStringExtra("modo")

        mercancia?.let {
            etIdMercancia.setText(it.id.toString())
            etProveedores.setText(it.proveedores_id.toString())
            etUsuariosM.setText(it.usuarios_id.toString())
            etFechaM.setText(it.fecha_ingreso)
            etRemesa.setText(it.numero_remesa)
            etOrigen.setText(it.origen_mercancia)
            etDestino.setText(it.destino_mercancia)
            etNombreR.setText(it.nombre_remitente)
            etDocumentoR.setText(it.documento_remitente)
            etDireccionR.setText(it.direccion_remitente)
            etCelularMR.setText(it.celular_remitente)
            etNombreMD.setText(it.nombre_destinatario)
            etDocumentoD.setText(it.documento_destinatario)
            etDireccionD.setText(it.direccion_destinatario)
            etCelularMD.setText(it.celular_destinatario)
            etValorD.setText(it.valor_declarado.toString())
            etValorF.setText(it.valor_flete.toString())
            etValorT.setText(it.valor_total.toString())
            etPeso.setText(it.peso.toString())
            etUnidades.setText(it.unidades.toString())
            etObservacionesM.setText(it.observaciones ?: "")
        }

        // Si es consulta -> bloquear inputs
        if (modo == "CONSULTA") {
            listOf(
                etProveedores, etUsuariosM, etFechaM, etRemesa,
                etOrigen, etDestino, etNombreR, etDocumentoR, etDireccionR,
                etCelularMR, etNombreMD, etDocumentoD, etDireccionD, etCelularMD,
                etValorD, etValorF, etValorT, etPeso, etUnidades, etObservacionesM
            ).forEach { it.isEnabled = false }


        }

        btnCancelar.setOnClickListener {
            Log.d("MercanciaDetalle", "Acción cancelar")
            finish()
        }
    }
}
