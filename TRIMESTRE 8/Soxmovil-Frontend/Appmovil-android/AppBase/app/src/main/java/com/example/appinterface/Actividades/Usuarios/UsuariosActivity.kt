package com.example.appinterface.Actividades.Usuarios

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.*
import com.example.appinterface.Actividades.Main.MainActivity
import com.example.appinterface.Api.Usuario.ApiUsuarios
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Adapter.Usuarios.UsuariosAdapter
import com.example.appinterface.Modelos.Usuarios.Usuarios
import com.google.android.material.snackbar.Snackbar
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UsuariosActivity : AppCompatActivity(), UsuariosAdapter.OnUsuarioClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UsuariosAdapter
    private val api: ApiUsuarios = RetrofitInstance.apiUsuarios

    private val usuarioFormLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val accion = result.data?.getStringExtra("accion") ?: "Usuario guardado"
            cargarUsuarios()
            mostrarSnackbarExito(accion)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios)

        recyclerView = findViewById(R.id.recyclerUsuarios)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UsuariosAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter

        // Botón nuevo usuario
        findViewById<Button>(R.id.btnNuevoUsuario).setOnClickListener {
            val intent = Intent(this, UsuarioFormActivity::class.java)
            usuarioFormLauncher.launch(intent)
        }

        val etBuscarCedula = findViewById<android.widget.EditText>(R.id.etBuscarCedula)
        findViewById<Button>(R.id.btnBuscarCedula).setOnClickListener {
            val cedulaTexto = etBuscarCedula.text.toString().trim()

            if (cedulaTexto.isEmpty()) {
                Toast.makeText(this, "Ingrese una cédula para buscar", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val id = cedulaTexto.toInt()
                    buscarUsuario(id)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "La cédula debe ser un número válido", Toast.LENGTH_SHORT).show()
                }
            }
        }


        // Botón exportar
        findViewById<Button>(R.id.btnDescargas).setOnClickListener {
            val opciones = arrayOf("📄 PDF", "📊 Excel", "📝 CSV", "📘 Word")
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Exportar usuarios")
            builder.setItems(opciones) { _, which ->
                when (which) {
                    0 -> exportarPDF()
                    1 -> exportarExcel()
                    2 -> exportarCSV()
                    3 -> exportarWord()
                }
            }
            builder.show()
        }

        // Botón volver
        findViewById<Button>(R.id.btnVolver).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        cargarUsuarios()
    }
    private fun buscarUsuario(id: Int) {
        api.getUsuario(id).enqueue(object : Callback<Usuarios> {
            override fun onResponse(call: Call<Usuarios>, response: Response<Usuarios>) {
                if (response.isSuccessful && response.body() != null) {
                    val usuarioEncontrado = response.body()!!
                    // Mostrar SOLO el usuario encontrado en el RecyclerView
                    adapter.updateData(listOf(usuarioEncontrado))
                    mostrarSnackbarExito("Usuario encontrado con ID: $id")
                } else {
                    Toast.makeText(this@UsuariosActivity, "No se encontró el usuario con ID: $id", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Usuarios>, t: Throwable) {
                Toast.makeText(this@UsuariosActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun cargarUsuarios() {
        api.getUsuarios().enqueue(object : Callback<List<Usuarios>> {
            override fun onResponse(call: Call<List<Usuarios>>, response: Response<List<Usuarios>>) {
                if (response.isSuccessful) {
                    adapter.updateData(response.body() ?: emptyList())
                } else {
                    Toast.makeText(
                        this@UsuariosActivity,
                        "Error al cargar usuarios: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Usuarios>>, t: Throwable) {
                Toast.makeText(this@UsuariosActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarSnackbarExito(mensaje: String) {
        val rootView = findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(rootView, mensaje, Snackbar.LENGTH_LONG)
        val textView = snackbar.view.findViewById<TextView>(
            com.google.android.material.R.id.snackbar_text
        )
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0)
        textView.compoundDrawablePadding = 16
        snackbar.show()
    }

    private fun exportarCSV() {
        Toast.makeText(this, "Archivo CSV generado ", Toast.LENGTH_SHORT).show()
    }

    private fun exportarPDF() {
        Toast.makeText(this, "Archivo PDF generado ", Toast.LENGTH_SHORT).show()
    }

    private fun exportarExcel() {
        exportarCSV()
        Toast.makeText(this, "Archivo Excel generado como CSV", Toast.LENGTH_SHORT).show()
    }

    private fun exportarWord() {
        Toast.makeText(this, "Archivo WORD generado ", Toast.LENGTH_SHORT).show()
    }

    // === Listeners del adapter ===
    override fun onConsultar(usuario: Usuarios) {
        val intent = Intent(this, UsuarioDetalleActivity::class.java)
        intent.putExtra("usuario", usuario)
        intent.putExtra("modo", "CONSULTA")
        startActivity(intent)
    }

    override fun onEditar(usuario: Usuarios) {
        val intent = Intent(this, UsuarioFormActivity::class.java)
        intent.putExtra("usuario", usuario)
        usuarioFormLauncher.launch(intent)
    }

    override fun onEliminar(usuario: Usuarios) {
        api.deleteUsuario(usuario.id).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    mostrarSnackbarExito("Usuario eliminado")
                    cargarUsuarios()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@UsuariosActivity, "Error eliminando", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


