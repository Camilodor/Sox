package com.example.appinterface.Actividades.Despachos

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.Despachos.ApiDespachos
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Modelos.Despachos.Despachos
import com.example.appinterface.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DespachoDetalleActivity : AppCompatActivity() {

    private val api: ApiDespachos = RetrofitInstance.apiDespachos
    private var despacho: Despachos? = null
    private var modo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despachos_detalle)

        val etIdDespacho: EditText = findViewById(R.id.etIdDespacho)
        val etMercanciaH: EditText = findViewById(R.id.etMercanciaH)
        val etVehiculos: EditText = findViewById(R.id.etVehiculos)
        val etUsuariosH: EditText = findViewById(R.id.etUsuariosH)
        val etTipopagoH: EditText = findViewById(R.id.etTipopagoH)
        val etFechaH: EditText = findViewById(R.id.etFechaH)
        val etNegociacion: EditText = findViewById(R.id.etNegociacion)
        val etAnticipo: EditText = findViewById(R.id.etAnticipo)
        val etSaldo: EditText = findViewById(R.id.etSaldo)
        val etObservacionesH: EditText = findViewById(R.id.etObservacionesH)
        val btnCancelar: Button = findViewById(R.id.btnCancelar)


        despacho = intent.getSerializableExtra("despacho") as? Despachos
        modo = intent.getStringExtra("modo")

        despacho?.let {
            etIdDespacho.setText(it.id.toString())
            etMercanciaH.setText(it.mercancias_id.toString())
            etVehiculos.setText(it.vehiculos_id.toString())
            etUsuariosH.setText(it.usuarios_id.toString())
            etTipopagoH.setText(it.tipo_pago_id.toString())
            etFechaH.setText(it.fecha_despacho)
            etNegociacion.setText(it.negociacion.toString())
            etAnticipo.setText(it.anticipo.toString())
            etSaldo.setText(it.saldo.toString())
            etObservacionesH.setText(it.observaciones_mer)

        }

        // Si es consulta -> bloquear inputs
        if (modo == "CONSULTA") {
            etMercanciaH.isEnabled = false
            etVehiculos.isEnabled = false
            etUsuariosH.isEnabled = false
            etTipopagoH.isEnabled = false
            etFechaH.isEnabled = false
            etNegociacion.isEnabled = false
            etAnticipo.isEnabled = false
            etSaldo.isEnabled = false
            etObservacionesH.isEnabled = false

        }



        btnCancelar.setOnClickListener {
            Log.d("UsuarioDetalle", "Acción cancelar")
            finish()
        }
    }
}
