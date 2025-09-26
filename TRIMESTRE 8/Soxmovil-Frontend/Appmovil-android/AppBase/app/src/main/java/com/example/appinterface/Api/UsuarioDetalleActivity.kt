package com.example.appinterface

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Api.ApiUsuarios
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioDetalleActivity : AppCompatActivity() {

    private val api: ApiUsuarios = RetrofitInstance.apiUsuarios
    private var usuario: Usuarios? = null
    private var modo: String? = null // "CONSULTA" o "EDICION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_detalle)

        // Referencias UI
        val etIdUsuario: EditText = findViewById(R.id.etIdUsuario)
        val etUsuario: EditText = findViewById(R.id.etUsuario)
        val etNombres: EditText = findViewById(R.id.etNombres)
        val etApellidos: EditText = findViewById(R.id.etApellidos)
        val etDocumento: EditText = findViewById(R.id.etDocumento)
        val etTelefono: EditText = findViewById(R.id.etTelefono)
        val etRol: EditText = findViewById(R.id.etRol)
        val btnGuardar: Button = findViewById(R.id.btnGuardar)
        val btnCancelar: Button = findViewById(R.id.btnCancelar)

        // Recibir usuario y modo desde el intent
        usuario = intent.getSerializableExtra("usuario") as? Usuarios
        modo = intent.getStringExtra("modo")

        usuario?.let {
            etIdUsuario.setText(it.id.toString())
            etUsuario.setText(it.nombre_usuario)
            etNombres.setText(it.nombres)
            etApellidos.setText(it.apellidos)
            etDocumento.setText(it.numero_documento.toString())
            etTelefono.setText(it.celular ?: "")
            etRol.setText(it.tipo_rol_id.toString())
        }

        // Si es consulta -> bloquear inputs
        if (modo == "CONSULTA") {
            etUsuario.isEnabled = false
            etNombres.isEnabled = false
            etApellidos.isEnabled = false
            etDocumento.isEnabled = false
            etTelefono.isEnabled = false
            etRol.isEnabled = false
            btnGuardar.isEnabled = false
        }

        // Guardar
        btnGuardar.setOnClickListener {
            val usuarioEditado = Usuarios(
                id = usuario?.id ?: 0,
                nombre_usuario = etUsuario.text.toString(),
                nombres = etNombres.text.toString(),
                apellidos = etApellidos.text.toString(),
                numero_documento = etDocumento.text.toString().toLong(),
                celular = etTelefono.text.toString(),
                direccion = usuario?.direccion ?: "",
                ciudad = usuario?.ciudad ?: "",
                email = usuario?.email ?: "",
                contrasena = usuario?.contrasena ?: "",
                tipo_documento_id = usuario?.tipo_documento_id ?: 1,
                tipo_rol_id = etRol.text.toString().toIntOrNull() ?: 1
            )

            Log.d("UsuarioDetalle", "Intentando actualizar usuario ID=${usuarioEditado.id}")

            api.updateUsuario(usuarioEditado.id, usuarioEditado)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("UsuarioDetalle", "Usuario actualizado correctamente")
                            Toast.makeText(
                                this@UsuarioDetalleActivity,
                                "Usuario actualizado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Log.e("UsuarioDetalle", "Error al actualizar: ${response.code()}")
                            Toast.makeText(
                                this@UsuarioDetalleActivity,
                                "Error al actualizar",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("UsuarioDetalle", "Fallo red: ${t.message}")
                        Toast.makeText(
                            this@UsuarioDetalleActivity,
                            "Error: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }

        btnCancelar.setOnClickListener {
            Log.d("UsuarioDetalle", "Acción cancelar")
            finish()
        }
    }
}
