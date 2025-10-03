package com.example.appinterface.Actividades.Proveedores

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.Proveedores.ApiProveedores
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Modelos.Proveedores.Proveedores
import com.example.appinterface.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProveedorDetalleActivity : AppCompatActivity() {

    private val api: ApiProveedores = RetrofitInstance.apiProveedores
    private var proveedor: Proveedores? = null
    private var modo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proveedores_detalle)


        val etIdUProveedor: EditText = findViewById(R.id.etIdProveedor)
        val etUsuariosP: EditText = findViewById(R.id.etUsuariosP)
        val etNombres: EditText = findViewById(R.id.etNombres)
        val etDescripcionP: EditText = findViewById(R.id.etDescripcionP)

        val btnCancelar: Button = findViewById(R.id.btnCancelar)


        proveedor = intent.getSerializableExtra("proveedor") as? Proveedores
        modo = intent.getStringExtra("modo")

        proveedor?.let {
            etIdUProveedor.setText(it.id.toString())
            etUsuariosP.setText(it.usuarios_id.toString())
            etNombres.setText(it.nombre)
            etDescripcionP.setText(it.descripcion)
        }


        if (modo == "CONSULTA") {
            etUsuariosP.isEnabled = false
            etNombres.isEnabled = false
            etDescripcionP.isEnabled = false

        }



        btnCancelar.setOnClickListener {
            Log.d("ProveedorDetalle", "Acción cancelar")
            finish()
        }
    }
}
