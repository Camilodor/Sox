package com.example.appinterface.Actividades.Main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appinterface.Actividades.Base.BaseActivity
import com.example.appinterface.Actividades.Login.LoginActivity
import com.example.appinterface.R
import com.google.android.material.card.MaterialCardView
import android.content.Intent

import com.example.appinterface.Actividades.Usuarios.UsuariosActivity
import com.example.appinterface.Actividades.Proveedores.ProveedoresActivity
import com.example.appinterface.Actividades.Mercancias.MercanciasActivity
import com.example.appinterface.Actividades.Despachos.DespachosActivity
import com.example.appinterface.Actividades.Entregas.EntregasActivity
import com.example.appinterface.Actividades.Devoluciones.DevolucionesActivity
import com.example.appinterface.Actividades.Seguimientos.SeguimientosActivity
import com.example.appinterface.Actividades.TipoPago.TipoPagoActivity

class MainActivity : BaseActivity() {

    private lateinit var tvUserInfo: TextView
    private lateinit var btnLogout: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvUserInfo = findViewById(R.id.tvUserInfo)
        btnLogout = findViewById(R.id.btnLogout)

        // Mostrar info del usuario logueado
        serviceAuth.me { success, error, user ->
            if(success && user != null){
                tvUserInfo.text = "Hola ${user.nombre_usuario}"
            } else {
                tvUserInfo.text = "No se pudo obtener usuario: $error"
            }
        }

        btnLogout.setOnClickListener {
            serviceAuth.logout { success, error ->
                if(success){
                    Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                    goToLogin()
                } else {
                    Toast.makeText(this, error ?: "Error al cerrar sesión", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Cards navegación
        findViewById<MaterialCardView>(R.id.cardUsuarios).setOnClickListener {
            startActivity(Intent(this, UsuariosActivity::class.java))
        }
        findViewById<MaterialCardView>(R.id.cardProveedores).setOnClickListener {
            startActivity(Intent(this, ProveedoresActivity::class.java))
        }
        findViewById<MaterialCardView>(R.id.cardMercancias).setOnClickListener {
            startActivity(Intent(this, MercanciasActivity::class.java))
        }
        findViewById<MaterialCardView>(R.id.cardDespachos).setOnClickListener {
            startActivity(Intent(this, DespachosActivity::class.java))
        }
        findViewById<MaterialCardView>(R.id.cardEntregas).setOnClickListener {
            startActivity(Intent(this, EntregasActivity::class.java))
        }
        findViewById<MaterialCardView>(R.id.cardDevoluciones).setOnClickListener {
            startActivity(Intent(this, DevolucionesActivity::class.java))
        }
        findViewById<MaterialCardView>(R.id.cardSeguimientos).setOnClickListener {
            startActivity(Intent(this, SeguimientosActivity::class.java))
        }
        findViewById<MaterialCardView>(R.id.cardTipoPago).setOnClickListener {
            startActivity(Intent(this, TipoPagoActivity::class.java))
        }
    }
}

