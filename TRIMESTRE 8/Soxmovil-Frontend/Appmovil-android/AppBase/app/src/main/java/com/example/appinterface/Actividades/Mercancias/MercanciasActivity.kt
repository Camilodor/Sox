package com.example.appinterface.Actividades.Mercancias

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
import com.example.appinterface.Api.Mercancias.ApiMercancias
import com.example.appinterface.Modelos.Mercancias.Mercancias
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Adapter.Mercancias.MercanciasAdapter
import com.example.appinterface.Actividades.Main.MainActivity
import com.example.appinterface.Modelos.Usuarios.Usuarios
import com.example.appinterface.R
import com.google.android.material.snackbar.Snackbar
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MercanciasActivity : AppCompatActivity(), MercanciasAdapter.OnMercanciaClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MercanciasAdapter
    private val api: ApiMercancias = RetrofitInstance.apiMercancias

    private val mercanciaFormLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val accion = result.data?.getStringExtra("accion") ?: "Mercancía guardada"
            cargarMercancias()
            mostrarSnackbarExito(accion)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mercancias)

        recyclerView = findViewById(R.id.recyclerMercancias)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MercanciasAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter

        // Botón nueva mercancía
        findViewById<Button>(R.id.btnNuevaMercancia).setOnClickListener {
            val intent = Intent(this, MercanciaFormActivity::class.java)
            mercanciaFormLauncher.launch(intent)
        }
        val etBuscarCedula = findViewById<android.widget.EditText>(R.id.etBuscarCedula)
        findViewById<Button>(R.id.btnBuscarCedula).setOnClickListener {
            val cedulaTexto = etBuscarCedula.text.toString().trim()

            if (cedulaTexto.isEmpty()) {
                Toast.makeText(this, "Ingrese el id para buscar", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val id = cedulaTexto.toInt()
                    buscarMercancia(id)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "el id debe ser un número válido", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Botón exportar
        findViewById<Button>(R.id.btnDescargas).setOnClickListener {
            val opciones = arrayOf("📄 PDF", "📊 Excel", "📝 CSV", "📘 Word")
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Exportar mercancías")
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

        cargarMercancias()
    }
    private fun buscarMercancia(id: Int) {
        api.getMercancia(id).enqueue(object : Callback<Mercancias> {
            override fun onResponse(call: Call<Mercancias>, response: Response<Mercancias>) {
                if (response.isSuccessful && response.body() != null) {
                    val mercanciaEncontrado = response.body()!!
                    adapter.updateData(listOf(mercanciaEncontrado))
                    mostrarSnackbarExito("Mercancia encontrada con ID: $id")
                } else {
                    Toast.makeText(this@MercanciasActivity, "No se encontró el mercancias con ID: $id", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Mercancias>, t: Throwable) {
                Toast.makeText(this@MercanciasActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarMercancias() {
        api.getMercancias().enqueue(object : Callback<List<Mercancias>> {
            override fun onResponse(call: Call<List<Mercancias>>, response: Response<List<Mercancias>>) {
                if (response.isSuccessful) {
                    adapter.updateData(response.body() ?: emptyList())
                } else {
                    Toast.makeText(
                        this@MercanciasActivity,
                        "Error al cargar mercancías: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Mercancias>>, t: Throwable) {
                Toast.makeText(this@MercanciasActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
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
    override fun onConsultar(mercancia: Mercancias) {
        val intent = Intent(this, MercanciaDetalleActivity::class.java)
        intent.putExtra("mercancia", mercancia)
        intent.putExtra("modo", "CONSULTA")
        startActivity(intent)
    }

    override fun onEditar(mercancia: Mercancias) {
        val intent = Intent(this, MercanciaFormActivity::class.java)
        intent.putExtra("mercancia", mercancia)
        mercanciaFormLauncher.launch(intent)
    }

    override fun onEliminar(mercancia: Mercancias) {
        mercancia.id?.let {
            api.deleteMercancia(it).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        mostrarSnackbarExito("Mercancía eliminada")
                        cargarMercancias()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MercanciasActivity, "Error eliminando", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
