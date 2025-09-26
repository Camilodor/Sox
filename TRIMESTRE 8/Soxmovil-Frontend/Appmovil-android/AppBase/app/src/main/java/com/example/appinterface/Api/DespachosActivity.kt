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
import com.example.appinterface.Adapter.DespachosAdapter
import com.example.appinterface.Adapter.MercanciasAdapter
import com.example.appinterface.Adapter.PersonaAdapter
import com.example.appinterface.Api.DataResponse
import com.example.appinterface.Api.Mercancias


class DespachosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var despachosAdapter: DespachosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_depachos)

        recyclerView = findViewById(R.id.recyclerDespachos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        obtenerDespachos()
    }

    private fun obtenerDespachos() {
        val call = RetrofitInstance.apiDespachos.getDespachos()
        call.enqueue(object : Callback<List<Despachos>> {
            override fun onResponse(
                call: Call<List<Despachos>>,
                response: Response<List<Despachos>>
            ) {
                if (response.isSuccessful) {
                    val listaDespachos = response.body() ?: emptyList()
                   despachosAdapter = DespachosAdapter(listaDespachos)
                    recyclerView.adapter = despachosAdapter
                } else {
                    Log.e("DespachosActivity", "Error en respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Despachos>>, t: Throwable) {
                Log.e("DespachosActivity", "Error: ${t.message}")
            }
        })
    }
}
