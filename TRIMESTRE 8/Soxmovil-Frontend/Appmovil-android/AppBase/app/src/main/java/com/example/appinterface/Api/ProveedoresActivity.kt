package com.example.appinterface

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Adapter.UsuariosAdapter
import com.example.appinterface.Api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import android.annotation.SuppressLint
import android.content.Intent
import android.widget.*
import com.example.appinterface.Adapter.PersonaAdapter
import com.example.appinterface.Adapter.ProveedoresAdapter
import com.example.appinterface.Api.DataResponse
import com.example.appinterface.Api.Proveedores



class ProveedoresActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var proveedoresAdapter: ProveedoresAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proveedores)

        recyclerView = findViewById(R.id.recyclerProveedores)
        recyclerView.layoutManager = LinearLayoutManager(this)

        obtenerProveedores()
    }

    private fun obtenerProveedores() {
        val call = RetrofitInstance.apiProveedores.getProveedores()
        call.enqueue(object : Callback<List<Proveedores>>{
            override fun onResponse(
                call: Call<List<Proveedores>>,
                response: Response<List<Proveedores>>
            ) {
                if (response.isSuccessful) {
                    val listaProveedores = response.body() ?: emptyList()
                    proveedoresAdapter = ProveedoresAdapter(listaProveedores)
                    recyclerView.adapter = proveedoresAdapter
                } else {
                    Log.e("ProveedoresActivity", "Error en respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Proveedores>>, t: Throwable) {
                Log.e("ProveedoresActivity", "Error: ${t.message}")
            }
        })
    }
}
