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
import com.example.appinterface.Adapter.MercanciasAdapter
import com.example.appinterface.Adapter.PersonaAdapter
import com.example.appinterface.Api.DataResponse
import com.example.appinterface.Api.Mercancias


class MercanciasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mercanciasAdapter: MercanciasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mercancias)

        recyclerView = findViewById(R.id.recyclerMercancias)
        recyclerView.layoutManager = LinearLayoutManager(this)

        obtenerMercancias()
    }

    private fun obtenerMercancias() {
        val call = RetrofitInstance.apiMercancias.getMercancias()
        call.enqueue(object : Callback<List<Mercancias>> {
            override fun onResponse(
                call: Call<List<Mercancias>>,
                response: Response<List<Mercancias>>
            ) {
                if (response.isSuccessful) {
                    val listaMercancias = response.body() ?: emptyList()
                    mercanciasAdapter = MercanciasAdapter(listaMercancias)
                    recyclerView.adapter = mercanciasAdapter
                } else {
                    Log.e("MercanciasActivity", "Error en respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Mercancias>>, t: Throwable) {
                Log.e("MercanciasActivity", "Error: ${t.message}")
            }
        })
    }
}
