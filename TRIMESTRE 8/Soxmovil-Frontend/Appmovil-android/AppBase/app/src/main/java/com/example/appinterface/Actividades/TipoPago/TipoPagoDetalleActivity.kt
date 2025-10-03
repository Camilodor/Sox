package com.example.appinterface.Actividades.TipoPago

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.TipoPago.ApiTipoPago
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import com.example.appinterface.Modelos.TipoPago.TipoPago
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TipoPagoDetalleActivity : AppCompatActivity() {

    private val api: ApiTipoPago = RetrofitInstance.apiTipoPago
    private var tipopago: TipoPago? = null
    private var modo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipospago_detalle)


        val etIdTIpoPago: EditText = findViewById(R.id.etIdTIpoPago)
        val etNombresP: EditText = findViewById(R.id.etNombresP)
        val btnCancelar: Button = findViewById(R.id.btnCancelar)





        tipopago = intent.getSerializableExtra("tipopago") as? TipoPago
        modo = intent.getStringExtra("modo")

        tipopago?.let {
            etIdTIpoPago.setText(it.id.toString())
            etNombresP.setText(it.nombre)

        }


        if (modo == "CONSULTA") {
            etNombresP.isEnabled = false

        }



        btnCancelar.setOnClickListener {
            Log.d("UsuarioDetalle", "Acción cancelar")
            finish()
        }
    }
}
