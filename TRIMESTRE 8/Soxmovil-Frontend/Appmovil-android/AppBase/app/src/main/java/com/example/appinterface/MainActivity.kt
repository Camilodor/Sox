package com.example.appinterface

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.annotation.SuppressLint
import android.content.Intent
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Adapter.PersonaAdapter
import com.example.appinterface.Api.DataResponse
import com.example.appinterface.Api.RetrofitInstance
import com.google.android.material.card.MaterialCardView


class MainActivity : AppCompatActivity() {
    private lateinit var persona: Persona

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val cardUsuarios: MaterialCardView = findViewById(R.id.cardUsuarios)
        cardUsuarios.setOnClickListener {
            val intent = Intent(this, UsuariosActivity::class.java)
            startActivity(intent)




    }
        val cardProveedores: MaterialCardView = findViewById(R.id.cardProveedores)
        cardProveedores.setOnClickListener {
            val intent = Intent(this, ProveedoresActivity::class.java)
            startActivity(intent)




        }
        val cardMercancias: MaterialCardView = findViewById(R.id.cardMercancias)
        cardMercancias.setOnClickListener {
            val intent = Intent(this, MercanciasActivity::class.java)
            startActivity(intent)




        }
        val cardDespachos: MaterialCardView = findViewById(R.id.cardDespachos)
        cardDespachos.setOnClickListener {
            val intent = Intent(this, DespachosActivity::class.java)
            startActivity(intent)
        }

        val cardEntregas: MaterialCardView = findViewById(R.id.cardEntregas)
        cardEntregas.setOnClickListener {
            val intent = Intent(this, EntregasActivity::class.java)
            startActivity(intent)
        }

    val cardDevoluciones: MaterialCardView = findViewById(R.id.cardDevoluciones)
    cardDevoluciones.setOnClickListener {
        val intent = Intent(this, DevolucionesActivity::class.java)
        startActivity(intent)
    }
        val cardSeguimientos: MaterialCardView = findViewById(R.id.cardSeguimientos)
        cardSeguimientos.setOnClickListener {
            val intent = Intent(this, SeguimientosActivity::class.java)
            startActivity(intent)
        }
        val cardTipoPago: MaterialCardView = findViewById(R.id.cardTipoPago)
        cardTipoPago.setOnClickListener {
            val intent = Intent(this, TipoPagoActivity::class.java)
            startActivity(intent)
        }




}}

