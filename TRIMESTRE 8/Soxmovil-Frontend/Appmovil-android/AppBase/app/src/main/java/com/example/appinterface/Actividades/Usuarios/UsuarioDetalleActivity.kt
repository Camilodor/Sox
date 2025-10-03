package com.example.appinterface.Actividades.Usuarios

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Api.Usuario.ApiUsuarios
import com.example.appinterface.R
import com.example.appinterface.Modelos.Usuarios.Usuarios
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioDetalleActivity : AppCompatActivity() {

    private val api: ApiUsuarios = RetrofitInstance.apiUsuarios
    private var usuario: Usuarios? = null
    private var modo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_detalle)


        val etIdUsuario: EditText = findViewById(R.id.etIdUsuario)
        val etUsuario: EditText = findViewById(R.id.etUsuario)
        val etNombres: EditText = findViewById(R.id.etNombres)
        val etApellidos: EditText = findViewById(R.id.etApellidos)
        val etDocumento: EditText = findViewById(R.id.etDocumento)
        val etTelefono: EditText = findViewById(R.id.etTelefono)
        val etRol: EditText = findViewById(R.id.etRol)
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

        }



        btnCancelar.setOnClickListener {
            Log.d("UsuarioDetalle", "Acción cancelar")
            finish()
        }
    }
}
