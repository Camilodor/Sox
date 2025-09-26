package com.example.appinterface

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.*
import com.example.appinterface.Adapter.*


class DevolucionesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var devolucionesAdapter: DevolucionesAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devoluciones)

        recyclerView = findViewById(R.id.recyclerDevoluciones)
        recyclerView.layoutManager = LinearLayoutManager(this)

        obtenerDevoluciones()
    }

    private fun obtenerDevoluciones() {
        val call = RetrofitInstance.apiDevoluciones.getDevoluciones()
        call.enqueue(object : Callback<List<Devoluciones>> {
            override fun onResponse(
                call: Call<List<Devoluciones>>,
                response: Response<List<Devoluciones>>
            ) {
                if (response.isSuccessful) {
                    val listaDevoluciones = response.body() ?: emptyList()
                    devolucionesAdapter= DevolucionesAdapter(listaDevoluciones)
                    recyclerView.adapter = devolucionesAdapter                } else {
                    Log.e("DevolucionesActivity", "Error en respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Devoluciones>>, t: Throwable) {
                Log.e("DevolucionesActivity", "Error: ${t.message}")
            }
        })
    }
}
