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
import com.example.appinterface.Adapter.*
import com.example.appinterface.Api.DataResponse
import com.example.appinterface.Api.Mercancias


class EntregasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var entregasAdapter: EntregasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entregas)

        recyclerView = findViewById(R.id.recyclerEntregas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        obtenerEntregas()
    }

    private fun obtenerEntregas() {
        val call = RetrofitInstance.apiEntregas.getEntregas()
        call.enqueue(object : Callback<List<Entregas>> {
            override fun onResponse(
                call: Call<List<Entregas>>,
                response: Response<List<Entregas>>
            ) {
                if (response.isSuccessful) {
                    val listaEntregas = response.body() ?: emptyList()
                    entregasAdapter= EntregasAdapter(listaEntregas)
                    recyclerView.adapter = entregasAdapter
                } else {
                    Log.e("EntregasActivity", "Error en respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Entregas>>, t: Throwable) {
                Log.e("EntregasActivity", "Error: ${t.message}")
            }
        })
    }
}
