package com.example.appinterface

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
import com.example.appinterface.Adapter.SeguimientosAdapter
import com.example.appinterface.Api.DataResponse
import com.example.appinterface.Api.Proveedores



class SeguimientosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var seguimientosAdapter: SeguimientosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seguimientos)

        recyclerView = findViewById(R.id.recyclerSeguimientos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        obtenerSeguimientos()
    }

    private fun obtenerSeguimientos() {
        val call = RetrofitInstance.apiSeguimientos.getSeguimientos()
        call.enqueue(object : Callback<List<Seguimientos>>{
            override fun onResponse(
                call: Call<List<Seguimientos>>,
                response: Response<List<Seguimientos>>
            ) {
                if (response.isSuccessful) {
                    val listaSeguimientos = response.body() ?: emptyList()
                    seguimientosAdapter = SeguimientosAdapter(listaSeguimientos)
                    recyclerView.adapter = seguimientosAdapter
                } else {
                    Log.e("SeguimientosActivity", "Error en respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Seguimientos>>, t: Throwable) {
                Log.e("SeguimientosActivity", "Error: ${t.message}")
            }
        })
    }
}
