package com.example.appinterface.Actividades.Usuarios

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Api.Usuario.ApiUsuarios
import com.example.appinterface.R
import com.example.appinterface.Modelos.Usuarios.Usuarios
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioFormActivity : AppCompatActivity() {

    private val api: ApiUsuarios = RetrofitInstance.apiUsuarios
    private var usuarioExistente: Usuarios? = null

    private lateinit var etUsuario: EditText
    private lateinit var etNombres: EditText
    private lateinit var etApellidos: EditText
    private lateinit var etDocumento: EditText
    private lateinit var spinnerTipoDocumento: Spinner
    private lateinit var etCelular: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etCiudad: EditText
    private lateinit var etEmail: EditText
    private lateinit var etContrasena: EditText
    private lateinit var spinnerRol: Spinner
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_form)

        // Referencias UI
        etUsuario = findViewById(R.id.etUsuario)
        etNombres = findViewById(R.id.etNombres)
        etApellidos = findViewById(R.id.etApellidos)
        etDocumento = findViewById(R.id.etDocumento)
        spinnerTipoDocumento = findViewById(R.id.spinnerTipoDocumento)
        etCelular = findViewById(R.id.etCelular)
        etDireccion = findViewById(R.id.etDireccion)
        etCiudad = findViewById(R.id.etCiudad)
        etEmail = findViewById(R.id.etEmail)
        etContrasena = findViewById(R.id.etContrasena)
        spinnerRol = findViewById(R.id.spinnerRol)
        btnGuardar = findViewById(R.id.btnGuardar)

        // llenar spinners
        val tiposDocumento = listOf("Cédula", "Tarjeta ID", "Pasaporte")
        val tiposRol = listOf("Administrador", "Bodeguero", "Conductor", "Cliente")
        spinnerTipoDocumento.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposDocumento)
        spinnerRol.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposRol)

        // precargar si es edición
        usuarioExistente = intent.getSerializableExtra("usuario") as? Usuarios
        usuarioExistente?.let {
            etUsuario.setText(it.nombre_usuario)
            etNombres.setText(it.nombres)
            etApellidos.setText(it.apellidos)
            etDocumento.setText(it.numero_documento.toString())
            etCelular.setText(it.celular)
            etDireccion.setText(it.direccion)
            etCiudad.setText(it.ciudad)
            etEmail.setText(it.email)
            etContrasena.setText(it.contrasena)

            if (it.tipo_documento_id > 0 && it.tipo_documento_id <= tiposDocumento.size) {
                spinnerTipoDocumento.setSelection(it.tipo_documento_id - 1)
            }
            if (it.tipo_rol_id > 0 && it.tipo_rol_id <= tiposRol.size) {
                spinnerRol.setSelection(it.tipo_rol_id - 1)
            }
        }

        // Guardar
        btnGuardar.setOnClickListener {
            if (etUsuario.text.isBlank() || etNombres.text.isBlank() || etEmail.text.isBlank()) {
                Toast.makeText(this, "Completa usuario, nombres y email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoUsuario = Usuarios(
                id = usuarioExistente?.id ?: 0,
                nombre_usuario = etUsuario.text.toString(),
                nombres = etNombres.text.toString(),
                apellidos = etApellidos.text.toString(),
                tipo_documento_id = spinnerTipoDocumento.selectedItemPosition + 1,
                numero_documento = etDocumento.text.toString().toLongOrNull() ?: 0L,
                celular = etCelular.text.toString(),
                direccion = etDireccion.text.toString(),
                ciudad = etCiudad.text.toString(),
                email = etEmail.text.toString(),
                contrasena = etContrasena.text.toString(),
                tipo_rol_id = spinnerRol.selectedItemPosition + 1
            )

            if (usuarioExistente == null) {
                Log.d("UsuarioForm", "Creando usuario: ${nuevoUsuario.nombre_usuario}")
                api.createUsuario(nuevoUsuario).enqueue(callbackConMensaje("Usuario creado"))
            } else {
                Log.d("UsuarioForm", "Actualizando usuario ID=${nuevoUsuario.id}")
                api.updateUsuario(nuevoUsuario.id, nuevoUsuario)
                    .enqueue(callbackConMensaje("Usuario actualizado"))
            }
        }
    }

    private fun callbackConMensaje(accion: String): Callback<ResponseBody> {
        return object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("UsuarioForm", "$accion exitosamente")
                    val bodyStr = response.body()?.string()
                    val json = try {
                        JSONObject(bodyStr ?: "{}")
                    } catch (e: Exception) {
                        JSONObject()
                    }
                    val msg = json.optString("message", accion)
                    Toast.makeText(this@UsuarioFormActivity, msg, Toast.LENGTH_SHORT).show()

                    // 👉 Notificar éxito al Activity anterior
                    setResult(RESULT_OK, Intent().apply {
                        putExtra("accion", accion) // "Usuario creado" o "Usuario actualizado"
                    })
                    finish()
                } else {
                    Log.e("UsuarioForm", "Error ${response.code()}")
                    Toast.makeText(
                        this@UsuarioFormActivity,
                        "Error ${response.code()}: ${response.errorBody()?.string()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("UsuarioForm", "Fallo red: ${t.message}")
                Toast.makeText(
                    this@UsuarioFormActivity,
                    "Error red: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
