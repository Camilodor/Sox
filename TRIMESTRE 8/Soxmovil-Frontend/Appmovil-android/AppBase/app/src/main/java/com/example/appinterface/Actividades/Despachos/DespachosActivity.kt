package com.example.appinterface.Actividades.Despachos

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.appinterface.Api.Despachos.ApiDespachos
import com.example.appinterface.Adapter.Despachos.DespachosAdapter
import com.example.appinterface.Modelos.Despachos.Despachos
import com.example.appinterface.Actividades.Main.MainActivity
import com.example.appinterface.Modelos.Usuarios.Usuarios
import com.example.appinterface.R


class DespachosActivity : AppCompatActivity(), DespachosAdapter.OnDespachoClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DespachosAdapter
    private val api: ApiDespachos = RetrofitInstance.apiDespachos

    private val despachoFormLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val accion = result.data?.getStringExtra("accion") ?: "Despacho guardado"
            cargarDespachos()
            mostrarSnackbarExito(accion)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_depachos)

        recyclerView = findViewById(R.id.recyclerDespachos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DespachosAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter



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
        val etBuscarCedula = findViewById<android.widget.EditText>(R.id.etBuscarCedula)
        findViewById<Button>(R.id.btnBuscarCedula).setOnClickListener {
            val cedulaTexto = etBuscarCedula.text.toString().trim()

            if (cedulaTexto.isEmpty()) {
                Toast.makeText(this, "Ingrese el id para buscar", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val id = cedulaTexto.toInt()
                    buscarDespacho(id)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "el id debe ser un número válido", Toast.LENGTH_SHORT).show()
                }
            }
        }

        findViewById<Button>(R.id.btnVolver).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        cargarDespachos()
    }
    private fun buscarDespacho(id: Int) {
        api.getDespacho(id).enqueue(object : Callback<Despachos> {
            override fun onResponse(call: Call<Despachos>, response: Response<Despachos>) {
                if (response.isSuccessful && response.body() != null) {
                    val despachoEncontrado = response.body()!!
                    adapter.updateData(listOf(despachoEncontrado))
                    mostrarSnackbarExito("Despacho encontrado con ID: $id")
                } else {
                    Toast.makeText(this@DespachosActivity, "No se encontró el despacho con ID: $id", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Despachos>, t: Throwable) {
                Toast.makeText(this@DespachosActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarDespachos() {
        api.getDespachos().enqueue(object : Callback<List<Despachos>> {
            override fun onResponse(call: Call<List<Despachos>>, response: Response<List<Despachos>>) {
                if (response.isSuccessful) {
                    adapter.updateData(response.body() ?: emptyList())
                } else {
                    Toast.makeText(
                        this@DespachosActivity,
                        "Error al cargar usuarios: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Despachos>>, t: Throwable) {
                Toast.makeText(this@DespachosActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
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

    override fun onConsultar(despacho: Despachos) {
        val intent = Intent(this, DespachoDetalleActivity::class.java)
        intent.putExtra("despacho", despacho)
        intent.putExtra("modo", "CONSULTA")
        startActivity(intent)
    }


}


