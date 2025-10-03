package com.example.appinterface.Actividades.Proveedores

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.Proveedores.ApiProveedores
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Modelos.Proveedores.Proveedores
import com.example.appinterface.R
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProveedorFormActivity : AppCompatActivity() {

    private val api: ApiProveedores = RetrofitInstance.apiProveedores
    private var proveedorExistente: Proveedores? = null

    private lateinit var etUsuariosP: EditText
    private lateinit var etNombres: EditText
    private lateinit var etDescripcionP: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proveedores_form)

        etUsuariosP = findViewById(R.id.etUsuariosP)
        etNombres = findViewById(R.id.etNombres)
        etDescripcionP = findViewById(R.id.etDescripcionP)
        btnGuardar = findViewById(R.id.btnGuardar)



        proveedorExistente = intent.getSerializableExtra("proveedor") as? Proveedores
        proveedorExistente?.let {
            etUsuariosP.setText(it.usuarios_id.toString())
            etNombres.setText(it.nombre)
            etDescripcionP.setText(it.descripcion)

        }


        btnGuardar.setOnClickListener {
            if (etUsuariosP.text.isBlank() || etNombres.text.isBlank() || etDescripcionP.text.isBlank()) {
                Toast.makeText(this, "Completa usuario, nombres y descripcion", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoProveedor = Proveedores(
                id = proveedorExistente?.id ?: 0,
                usuarios_id = etUsuariosP.text.toString().toInt(),
                nombre = etNombres.text.toString(),
                descripcion = etDescripcionP.text.toString(),
            )

            if (proveedorExistente == null) {
                Log.d("ProveedorForm", "Creando proveedor: ${nuevoProveedor.nombre}")
                api.createProveedor(nuevoProveedor).enqueue(callbackConMensaje("Proveedor creado"))
            } else {
                Log.d("ProveedorForm", "Actualizando proveedor ID=${nuevoProveedor.id}")
                nuevoProveedor.id?.let { it1 ->
                    api.updateProveedor(it1, nuevoProveedor)
                        .enqueue(callbackConMensaje("Proveedor actualizado"))
                }
            }
        }
    }

    private fun callbackConMensaje(accion: String): Callback<ResponseBody> {
        return object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("ProveedorForm", "$accion exitosamente")
                    val bodyStr = response.body()?.string()
                    val json = try {
                        JSONObject(bodyStr ?: "{}")
                    } catch (e: Exception) {
                        JSONObject()
                    }
                    val msg = json.optString("message", accion)
                    Toast.makeText(this@ProveedorFormActivity, msg, Toast.LENGTH_SHORT).show()


                    setResult(RESULT_OK, Intent().apply {
                        putExtra("accion", accion)
                    })
                    finish()
                } else {
                    Log.e("ProveedorForm", "Error ${response.code()}")
                    Toast.makeText(
                        this@ProveedorFormActivity,
                        "Error ${response.code()}: ${response.errorBody()?.string()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("ProveedorForm", "Fallo red: ${t.message}")
                Toast.makeText(
                    this@ProveedorFormActivity,
                    "Error red: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
