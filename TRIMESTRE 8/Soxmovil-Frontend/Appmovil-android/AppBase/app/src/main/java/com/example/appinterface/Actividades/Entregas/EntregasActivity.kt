package com.example.appinterface.Actividades.Entregas

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
import com.example.appinterface.Api.RetrofitInstance
import com.google.android.material.snackbar.Snackbar
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.appinterface.Api.Entregas.ApiEntregas
import com.example.appinterface.Adapter.Entregas.EntregasAdapter
import com.example.appinterface.Actividades.Main.MainActivity
import com.example.appinterface.R
import com.example.appinterface.Modelos.Entregas.Entregas
import com.example.appinterface.Modelos.Usuarios.Usuarios


class EntregasActivity : AppCompatActivity(), EntregasAdapter.OnEntregaClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EntregasAdapter
    private val api: ApiEntregas = RetrofitInstance.apiEntregas

    private val entregaFormLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val accion = result.data?.getStringExtra("accion") ?: "Entrega guardada"
            cargarEntregas()
            mostrarSnackbarExito(accion)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entregas)

        recyclerView = findViewById(R.id.recyclerEntregas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EntregasAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter


        findViewById<Button>(R.id.btnNuevoEntrega).setOnClickListener {
            val intent = Intent(this, EntregaFormActivity::class.java)
            entregaFormLauncher.launch(intent)
        }
        val etBuscarCedula = findViewById<android.widget.EditText>(R.id.etBuscarCedula)
        findViewById<Button>(R.id.btnBuscarCedula).setOnClickListener {
            val cedulaTexto = etBuscarCedula.text.toString().trim()

            if (cedulaTexto.isEmpty()) {
                Toast.makeText(this, "Ingrese el id para buscar", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val id = cedulaTexto.toInt()
                    buscarEntrega(id)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "el id debe ser un número válido", Toast.LENGTH_SHORT).show()
                }
            }
        }


        findViewById<Button>(R.id.btnDescargas).setOnClickListener {
            val opciones = arrayOf("📄 PDF", "📊 Excel", "📝 CSV", "📘 Word")
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Exportar entregas")
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

        cargarEntregas()
    }
    private fun buscarEntrega(id: Int) {
        api.getEntrega(id).enqueue(object : Callback<Entregas> {
            override fun onResponse(call: Call<Entregas>, response: Response<Entregas>) {
                if (response.isSuccessful && response.body() != null) {
                    val entregaEncontrada = response.body()!!
                    adapter.updateData(listOf(entregaEncontrada))
                    mostrarSnackbarExito("Entrega encontrada con ID: $id")
                } else {
                    Toast.makeText(this@EntregasActivity, "No se encontró la entrega con ID: $id", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Entregas>, t: Throwable) {
                Toast.makeText(this@EntregasActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarEntregas() {
        api.getEntregas().enqueue(object : Callback<List<Entregas>> {
            override fun onResponse(call: Call<List<Entregas>>, response: Response<List<Entregas>>) {
                if (response.isSuccessful) {
                    adapter.updateData(response.body() ?: emptyList())
                } else {
                    Toast.makeText(
                        this@EntregasActivity,
                        "Error al cargar entregas: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Entregas>>, t: Throwable) {
                Toast.makeText(this@EntregasActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
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

    // --- Exportadores (pendiente implementar lógica) ---
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
    override fun onConsultar(entrega: Entregas) {
        val intent = Intent(this, EntregaDetalleActivity::class.java)
        intent.putExtra("entrega", entrega)
        intent.putExtra("modo", "CONSULTA")
        startActivity(intent)
    }

    override fun onEditar(entrega: Entregas) {
        val intent = Intent(this, EntregaFormActivity::class.java)
        intent.putExtra("entrega", entrega)
        entregaFormLauncher.launch(intent)
    }

    override fun onEliminar(entrega: Entregas) {
        api.deleteEntrega(entrega.id).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    mostrarSnackbarExito("Entrega eliminada")
                    cargarEntregas()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@EntregasActivity, "Error eliminando", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

