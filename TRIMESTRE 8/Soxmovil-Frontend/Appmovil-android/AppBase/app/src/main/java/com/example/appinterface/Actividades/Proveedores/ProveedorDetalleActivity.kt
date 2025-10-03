package com.example.appinterface.Actividades.Proveedores

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.Proveedores.ApiProveedores
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Modelos.Proveedores.Proveedores
import com.example.appinterface.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProveedorDetalleActivity : AppCompatActivity() {

    private val api: ApiProveedores = RetrofitInstance.apiProveedores
    private var proveedor: Proveedores? = null
    private var modo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proveedores_detalle)


        val etIdUProveedor: EditText = findViewById(R.id.etIdProveedor)
        val etUsuariosP: EditText = findViewById(R.id.etUsuariosP)
        val etNombres: EditText = findViewById(R.id.etNombres)
        val etDescripcionP: EditText = findViewById(R.id.etDescripcionP)
        val btnGuardar: Button = findViewById(R.id.btnGuardar)
        val btnCancelar: Button = findViewById(R.id.btnCancelar)


        proveedor = intent.getSerializableExtra("proveedor") as? Proveedores
        modo = intent.getStringExtra("modo")

        proveedor?.let {
            etIdUProveedor.setText(it.id.toString())
            etUsuariosP.setText(it.usuarios_id.toString())
            etNombres.setText(it.nombre)
            etDescripcionP.setText(it.descripcion)
        }


        if (modo == "CONSULTA") {
            etUsuariosP.isEnabled = false
            etNombres.isEnabled = false
            etDescripcionP.isEnabled = false
            btnGuardar.isEnabled = false
        }


        btnGuardar.setOnClickListener {
            val proveedorEditado = Proveedores(
                id = proveedor?.id ?: 0,
                usuarios_id = etUsuariosP.text.toString().toInt(),
                nombre = etNombres.text.toString(),
                descripcion = etDescripcionP.text.toString(),

            )

            Log.d("ProveedorDetalle", "Intentando actualizar proveedor ID=${proveedorEditado.id}")

            proveedorEditado.id?.let { it1 ->
                api.updateProveedor(it1, proveedorEditado)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                                Log.d("ProveedorDetalle", "Proveedor actualizado correctamente")
                                Toast.makeText(
                                    this@ProveedorDetalleActivity,
                                    "Proveedor actualizado correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Log.e("ProveedorDetalle", "Error al actualizar: ${response.code()}")
                                Toast.makeText(
                                    this@ProveedorDetalleActivity,
                                    "Error al actualizar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.e("ProveedorDetalle", "Fallo red: ${t.message}")
                            Toast.makeText(
                                this@ProveedorDetalleActivity,
                                "Error: ${t.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
        }

        btnCancelar.setOnClickListener {
            Log.d("ProveedorDetalle", "Acción cancelar")
            finish()
        }
    }
}
