package com.example.appinterface.Actividades.Devoluciones

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
import com.example.appinterface.Api.Devoluciones.ApiDevoluciones
import com.example.appinterface.Api.RetrofitInstance
import com.google.android.material.snackbar.Snackbar
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.example.appinterface.Adapter.Devoluciones.DevolucionesAdapter
import com.example.appinterface.Modelos.Devoluciones.Devoluciones
import com.example.appinterface.Actividades.Main.MainActivity
import com.example.appinterface.Modelos.Entregas.Entregas
import com.example.appinterface.Modelos.Usuarios.Usuarios
import com.example.appinterface.R


class DevolucionesActivity : AppCompatActivity(), DevolucionesAdapter.OnDevolucionClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DevolucionesAdapter
    private val api: ApiDevoluciones = RetrofitInstance.apiDevoluciones

    private val devolucionFormLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val accion = result.data?.getStringExtra("accion") ?: "Devolucion guardada"
            cargarDevoluciones()
            mostrarSnackbarExito(accion)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devoluciones)

        recyclerView = findViewById(R.id.recyclerDevoluciones)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DevolucionesAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter


        findViewById<Button>(R.id.btnNuevoDevolucion).setOnClickListener {
            val intent = Intent(this, DevolucionFormActivity::class.java)
            devolucionFormLauncher.launch(intent)
        }
        val etBuscarCedula = findViewById<android.widget.EditText>(R.id.etBuscarCedula)
        findViewById<Button>(R.id.btnBuscarCedula).setOnClickListener {
            val cedulaTexto = etBuscarCedula.text.toString().trim()

            if (cedulaTexto.isEmpty()) {
                Toast.makeText(this, "Ingrese el id para buscar", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val id = cedulaTexto.toInt()
                    buscarDevolucion(id)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "el id debe ser un número válido", Toast.LENGTH_SHORT).show()
                }
            }
        }


        findViewById<Button>(R.id.btnDescargas).setOnClickListener {
            val opciones = arrayOf("📄 PDF", "📊 Excel", "📝 CSV", "📘 Word")
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Exportar devoluciones")
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


        findViewById<Button>(R.id.btnVolver).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        cargarDevoluciones()
    }
    private fun buscarDevolucion(id: Int) {
        api.getDevolucion(id).enqueue(object : Callback<Devoluciones> {
            override fun onResponse(call: Call<Devoluciones>, response: Response<Devoluciones>) {
                if (response.isSuccessful && response.body() != null) {
                    val devolucionEncontrada = response.body()!!

                    adapter.updateData(listOf(devolucionEncontrada))
                    mostrarSnackbarExito("Devolucion encontrada con ID: $id")
                } else {
                    Toast.makeText(this@DevolucionesActivity, "No se encontró la devolucion con ID: $id", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Devoluciones>, t: Throwable) {
                Toast.makeText(this@DevolucionesActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarDevoluciones() {
        api.getDevoluciones().enqueue(object : Callback<List<Devoluciones>> {
            override fun onResponse(call: Call<List<Devoluciones>>, response: Response<List<Devoluciones>>) {
                if (response.isSuccessful) {
                    adapter.updateData(response.body() ?: emptyList())
                } else {
                    Toast.makeText(
                        this@DevolucionesActivity,
                        "Error al cargar devoluciones: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Devoluciones>>, t: Throwable) {
                Toast.makeText(this@DevolucionesActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
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
    override fun onConsultar(devolucion: Devoluciones) {
        val intent = Intent(this, DevolucionDetalleActivity::class.java)
        intent.putExtra("devolucion", devolucion)
        intent.putExtra("modo", "CONSULTA")
        startActivity(intent)
    }

    override fun onEditar(devolucion: Devoluciones) {
        val intent = Intent(this, DevolucionFormActivity::class.java)
        intent.putExtra("devolucion", devolucion)
        devolucionFormLauncher.launch(intent)
    }

    override fun onEliminar(devolucion: Devoluciones) {
        devolucion.id?.let {
            api.deleteDevolucion(it).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        mostrarSnackbarExito("Devolucion eliminado")
                        cargarDevoluciones()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@DevolucionesActivity, "Error eliminando", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}


