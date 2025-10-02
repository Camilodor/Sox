package com.example.appinterface.Actividades.Seguimientos

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.Seguimientos.ApiSeguimientos
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import com.example.appinterface.Modelos.Seguimientos.Seguimientos

class SeguimientoDetalleActivity : AppCompatActivity() {

    private val api: ApiSeguimientos = RetrofitInstance.apiSeguimientos
    private var seguimiento: Seguimientos? = null
    private var modo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seguimientos_detalle)


        val etIdSeguimientos: EditText = findViewById(R.id.etIdSeguimientos)
        val etMercanciasS: EditText = findViewById(R.id.etMercanciasS)
        val etEstadoS: EditText = findViewById(R.id.etEstadoS)
        val etObservacionesS: EditText = findViewById(R.id.etObservacionesS)
        val btnCancelar: Button = findViewById(R.id.btnCancelar)


        seguimiento = intent.getSerializableExtra("seguimiento") as? Seguimientos
        modo = intent.getStringExtra("modo")

        seguimiento?.let {
            etIdSeguimientos.setText(it.id.toString())
            etMercanciasS.setText(it.mercancias_id.toString())
            etEstadoS.setText(it.estado)
            etObservacionesS.setText(it.observaciones)
        }
        if (modo == "CONSULTA") {
            etMercanciasS.isEnabled = false
            etEstadoS.isEnabled = false
            etObservacionesS.isEnabled = false

        }

        btnCancelar.setOnClickListener {
            Log.d("UsuarioDetalle", "Acción cancelar")
            finish()
        }
    }
}
