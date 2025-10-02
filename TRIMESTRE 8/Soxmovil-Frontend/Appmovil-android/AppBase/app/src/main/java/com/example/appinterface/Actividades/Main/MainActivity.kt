package com.example.appinterface.Actividades.Main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.annotation.SuppressLint
import android.content.Intent
import com.example.appinterface.Actividades.Despachos.DespachosActivity
import com.example.appinterface.Actividades.Devoluciones.DevolucionesActivity
import com.example.appinterface.Actividades.Entregas.EntregasActivity
import com.example.appinterface.Actividades.Mercancias.MercanciasActivity
import com.example.appinterface.Actividades.Proveedores.ProveedoresActivity
import com.example.appinterface.Actividades.Seguimientos.SeguimientosActivity
import com.example.appinterface.Actividades.TipoPago.TipoPagoActivity
import com.example.appinterface.Actividades.Usuarios.UsuariosActivity
import com.example.appinterface.R
import com.google.android.material.card.MaterialCardView


class MainActivity : AppCompatActivity() {


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

