package com.example.appinterface.Actividades.Proveedores

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
import com.example.appinterface.Api.Proveedores.ApiProveedores
import com.example.appinterface.Modelos.Proveedores.Proveedores
import com.example.appinterface.Adapter.Proveedores.ProveedoresAdapter
import com.example.appinterface.Actividades.Main.MainActivity
import com.example.appinterface.R


class ProveedoresActivity : AppCompatActivity(), ProveedoresAdapter.OnProveedorClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProveedoresAdapter
    private val api: ApiProveedores = RetrofitInstance.apiProveedores

    private val proveedorFormLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val accion = result.data?.getStringExtra("accion") ?: "Proveedor guardado"
            cargarProveedores()
            mostrarSnackbarExito(accion)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proveedores)

        recyclerView = findViewById(R.id.recyclerProveedores)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProveedoresAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter


        findViewById<Button>(R.id.btnNuevoProveedor).setOnClickListener {
            val intent = Intent(this, ProveedorFormActivity::class.java)
            proveedorFormLauncher.launch(intent)
        }


        findViewById<Button>(R.id.btnDescargas).setOnClickListener {
            val opciones = arrayOf("📄 PDF", "📊 Excel", "📝 CSV", "📘 Word")
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Exportar proveedores")
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

        cargarProveedores()
    }

    private fun cargarProveedores() {
        api.getProveedores().enqueue(object : Callback<List<Proveedores>> {
            override fun onResponse(call: Call<List<Proveedores>>, response: Response<List<Proveedores>>) {
                if (response.isSuccessful) {
                    adapter.updateData(response.body() ?: emptyList())
                } else {
                    Toast.makeText(
                        this@ProveedoresActivity,
                        "Error al cargar proveedores: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Proveedores>>, t: Throwable) {
                Toast.makeText(this@ProveedoresActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
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
        /* tu lógica */
    }

    private fun exportarPDF() {
        /* tu lógica */
    }

    private fun exportarExcel() {
        exportarCSV()
        Toast.makeText(this, "Archivo Excel generado como CSV", Toast.LENGTH_SHORT).show()
    }

    private fun exportarWord() {
        /* tu lógica */
    }

    // === Listeners del adapter ===
    override fun onConsultar(proveedor: Proveedores) {
        val intent = Intent(this, ProveedorDetalleActivity::class.java)
        intent.putExtra("proveedor", proveedor)
        intent.putExtra("modo", "CONSULTA")
        startActivity(intent)
    }

    override fun onEditar(proveedor: Proveedores) {
        val intent = Intent(this, ProveedorFormActivity::class.java)
        intent.putExtra("proveedor", proveedor)
        proveedorFormLauncher.launch(intent)
    }

    override fun onEliminar(proveedor: Proveedores) {
        proveedor.id?.let {
            api.deleteProveedor(it).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        mostrarSnackbarExito("Proveedor eliminado")
                        cargarProveedores()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@ProveedoresActivity, "Error eliminando", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}


