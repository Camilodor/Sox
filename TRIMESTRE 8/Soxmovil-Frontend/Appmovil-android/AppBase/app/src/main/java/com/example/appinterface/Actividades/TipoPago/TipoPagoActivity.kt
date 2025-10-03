package com.example.appinterface.Actividades.TipoPago

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
import com.example.appinterface.Api.TipoPago.ApiTipoPago
import com.example.appinterface.Adapter.TipoPago.TipoPagoAdapter
import com.example.appinterface.Actividades.Main.MainActivity
import com.example.appinterface.R
import com.example.appinterface.Modelos.TipoPago.TipoPago
import com.example.appinterface.Modelos.Usuarios.Usuarios


class TipoPagoActivity : AppCompatActivity(), TipoPagoAdapter.OnTipopagoClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TipoPagoAdapter
    private val api: ApiTipoPago = RetrofitInstance.apiTipoPago

    private val tipopagoFormLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val accion = result.data?.getStringExtra("accion") ?: "Tipo de pago guardado"
            cargarTipospago()
            mostrarSnackbarExito(accion)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipospago)

        recyclerView = findViewById(R.id.recyclerTipospago)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TipoPagoAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter



        findViewById<Button>(R.id.btnDescargas).setOnClickListener {
            val opciones = arrayOf("📄 PDF", "📊 Excel", "📝 CSV", "📘 Word")
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Exportar tipos de pago")
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
                    buscarTipopago(id)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "el id debe ser un número válido", Toast.LENGTH_SHORT).show()
                }
            }
        }


        findViewById<Button>(R.id.btnVolver).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        cargarTipospago()
    }
    private fun buscarTipopago(id: Int) {
        api.getTipopagoPorId(id).enqueue(object : Callback<TipoPago> {
            override fun onResponse(call: Call<TipoPago>, response: Response<TipoPago>) {
                if (response.isSuccessful && response.body() != null) {
                    val tipopagoEncontrado = response.body()!!
                    adapter.updateData(listOf(tipopagoEncontrado))
                    mostrarSnackbarExito("Tipo de pago  encontrado con ID: $id")
                } else {
                    Toast.makeText(this@TipoPagoActivity, "No se encontró el Tipo pago con ID: $id", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TipoPago>, t: Throwable) {
                Toast.makeText(this@TipoPagoActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarTipospago() {
        api.getTipospago().enqueue(object : Callback<List<TipoPago>> {
            override fun onResponse(call: Call<List<TipoPago>>, response: Response<List<TipoPago>>) {
                if (response.isSuccessful) {
                    adapter.updateData(response.body() ?: emptyList())
                } else {
                    Toast.makeText(
                        this@TipoPagoActivity,
                        "Error al cargar tipo de pago: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<TipoPago>>, t: Throwable) {
                Toast.makeText(this@TipoPagoActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
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

    override fun onConsultar(tipopago: TipoPago) {
        val intent = Intent(this, TipoPagoDetalleActivity::class.java)
        intent.putExtra("tipopago", tipopago)
        intent.putExtra("modo", "CONSULTA")
        startActivity(intent)
    }


}


