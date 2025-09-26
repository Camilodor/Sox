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
import com.example.appinterface.Adapter.TipoPagoAdapter
import com.example.appinterface.Api.DataResponse


class TipoPagoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tipoPagoAdapter: TipoPagoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipospago)

        recyclerView = findViewById(R.id.recyclerTipoPago)
        recyclerView.layoutManager = LinearLayoutManager(this)

        obtenerTipospago()
    }

    private fun obtenerTipospago() {
        val call = RetrofitInstance.apiTipoPago.getTipospago()
        call.enqueue(object : Callback<List<TipoPago>> {
            override fun onResponse(
                call: Call<List<TipoPago>>,
                response: Response<List<TipoPago>>
            ) {
                if (response.isSuccessful) {
                    val listaTipoPago = response.body() ?: emptyList()
                    tipoPagoAdapter = TipoPagoAdapter(listaTipoPago)
                    recyclerView.adapter = tipoPagoAdapter
                } else {
                    Log.e("TIpoPagoActivity", "Error en respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<TipoPago>>, t: Throwable) {
                Log.e("TipoPagoActivity", "Error: ${t.message}")
            }
        })
    }
}
