package com.example.appinterface.Actividades.Usuarios

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
        val btnGuardar: Button = findViewById(R.id.btnGuardar)




        tipopago = intent.getSerializableExtra("tipopago") as? TipoPago
        modo = intent.getStringExtra("modo")

        tipopago?.let {
            etIdTIpoPago.setText(it.id.toString())
            etNombresP.setText(it.nombre)

        }


        if (modo == "CONSULTA") {
            etNombresP.isEnabled = false
            btnGuardar.isEnabled = false
        }


        btnGuardar.setOnClickListener {
            val tipopagoEditado = TipoPago(
                id = tipopago?.id ?: 0,
                nombre = etNombresP.text.toString(),

            )

            Log.d("TipoPagoDetalle", "Intentando actualizar el tipo de pago ID=${tipopagoEditado.id}")

            tipopagoEditado.id?.let { it1 ->
                api.updateTipopago(it1, tipopagoEditado)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                                Log.d("TipoPagoDetalle", "Tipo de pago  actualizado correctamente")
                                Toast.makeText(
                                    this@TipoPagoDetalleActivity,
                                    "Tipo de pago actualizado correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Log.e("TipoPagoDetalle", "Error al actualizar: ${response.code()}")
                                Toast.makeText(
                                    this@TipoPagoDetalleActivity,
                                    "Error al actualizar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.e("TipoPagoDetalle", "Fallo red: ${t.message}")
                            Toast.makeText(
                                this@TipoPagoDetalleActivity,
                                "Error: ${t.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
        }

        btnCancelar.setOnClickListener {
            Log.d("UsuarioDetalle", "Acción cancelar")
            finish()
        }
    }
}
