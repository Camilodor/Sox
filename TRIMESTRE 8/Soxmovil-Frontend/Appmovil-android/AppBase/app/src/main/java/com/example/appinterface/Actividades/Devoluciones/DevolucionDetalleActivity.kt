package com.example.appinterface.Actividades.Devoluciones

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.Devoluciones.ApiDevoluciones
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Modelos.Devoluciones.Devoluciones
import com.example.appinterface.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DevolucionDetalleActivity : AppCompatActivity() {

    private val api: ApiDevoluciones = RetrofitInstance.apiDevoluciones
    private var devolucion: Devoluciones? = null
    private var modo: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devoluciones_detalle)

        val etIdDevolucion: EditText = findViewById(R.id.etIdDevolucion)
        val etMercanciaD: EditText = findViewById(R.id.etMercanciaD)
        val etDespachosD: EditText = findViewById(R.id.etDespachosD)
        val etUsuariosD: EditText = findViewById(R.id.etUsuariosD)
        val etFechaD: EditText = findViewById(R.id.etFechaD)
        val etMotivo: EditText = findViewById(R.id.etMotivo)
        val etEstadoD: EditText = findViewById(R.id.etEstadoD)
        val etObservacionesD: EditText = findViewById(R.id.etObservacionesD)
        val btnCancelar: Button = findViewById(R.id.btnCancelar)


        devolucion = intent.getSerializableExtra("devolucion") as? Devoluciones
        modo = intent.getStringExtra("modo")

        devolucion?.let {
            etIdDevolucion.setText(it.id.toString())
            etMercanciaD.setText(it.mercancias_id.toString())
            etDespachosD.setText(it.despachos_id.toString())
            etUsuariosD.setText(it.usuarios_id.toString())
            etFechaD.setText(it.fecha_devolucion)
            etMotivo.setText(it.motivo_devolucion)
            etEstadoD.setText(it.estado_devolucion)
            etObservacionesD.setText(it.observaciones)
        }


        if (modo == "CONSULTA") {
            etMercanciaD.isEnabled = false
            etDespachosD.isEnabled = false
            etUsuariosD.isEnabled = false
            etFechaD.isEnabled = false
            etMotivo.isEnabled = false
            etEstadoD.isEnabled = false
            etObservacionesD.isEnabled = false

        }


        btnCancelar.setOnClickListener {
            Log.d("DevolucionDetalle", "Acción cancelar")
            finish()
        }
    }
}


