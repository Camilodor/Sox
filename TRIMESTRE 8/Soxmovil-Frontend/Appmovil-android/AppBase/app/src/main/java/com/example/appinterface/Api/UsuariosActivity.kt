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
import com.example.appinterface.Api.DataResponse


class UsuariosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var usuariosAdapter: UsuariosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios)

        recyclerView = findViewById(R.id.recyclerUsuarios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        obtenerUsuarios()
    }

    private fun obtenerUsuarios() {
        val call = RetrofitInstance.apiUsuarios.getUsuarios()
        call.enqueue(object : Callback<List<Usuarios>> {
            override fun onResponse(
                call: Call<List<Usuarios>>,
                response: Response<List<Usuarios>>
            ) {
                if (response.isSuccessful) {
                    val listaUsuarios = response.body() ?: emptyList()
                    usuariosAdapter = UsuariosAdapter(listaUsuarios)
                    recyclerView.adapter = usuariosAdapter
                } else {
                    Log.e("UsuariosActivity", "Error en respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Usuarios>>, t: Throwable) {
                Log.e("UsuariosActivity", "Error: ${t.message}")
            }
        })
    }
}
