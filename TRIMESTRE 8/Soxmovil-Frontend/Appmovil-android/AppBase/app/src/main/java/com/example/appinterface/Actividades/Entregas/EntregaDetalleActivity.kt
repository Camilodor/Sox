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

        }


        btnCancelar.setOnClickListener {
            Log.d("EntregaDetalle", "Acción cancelar")
            finish()
        }
    }
}
