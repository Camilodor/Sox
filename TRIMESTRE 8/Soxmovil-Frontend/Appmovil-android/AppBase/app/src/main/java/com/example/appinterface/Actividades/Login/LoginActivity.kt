package com.example.appinterface.Actividades.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.appinterface.Actividades.Main.MainActivity
import com.example.appinterface.R
import com.example.appinterface.Services.ServiceAuth
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var serviceAuth: ServiceAuth
    private lateinit var etLogin: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        serviceAuth = ServiceAuth(this)

        // Si ya hay token, ir directo a MainActivity
        serviceAuth.getToken()?.let {
            if(it.isNotEmpty()){
                goToMainActivity()
                return
            }
        }

        etLogin = findViewById(R.id.etLogin)
        etContrasena = findViewById(R.id.etContrasena)
        btnLogin = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)

        btnLogin.setOnClickListener {
            val login = etLogin.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if(login.isEmpty() || contrasena.isEmpty()){
                Toast.makeText(this, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show()
            } else {
                doLogin(login, contrasena)
            }
        }
    }

    private fun doLogin(login: String, contrasena: String) {
        progressBar.visibility = ProgressBar.VISIBLE
        serviceAuth.login(login, contrasena) { success, errorMessage, user ->
            progressBar.visibility = ProgressBar.GONE
            if(success && user != null){
                Toast.makeText(this, "Bienvenido ${user.nombre_usuario}", Toast.LENGTH_SHORT).show()
                goToMainActivity()
            } else {
                Toast.makeText(this, errorMessage ?: "Error desconocido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}


