package com.example.appinterface.Actividades.Base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Actividades.Login.LoginActivity
import com.example.appinterface.Services.ServiceAuth

open class BaseActivity : AppCompatActivity() {

    protected lateinit var serviceAuth: ServiceAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceAuth = ServiceAuth(this)

        // Validar token al iniciar
        val token = serviceAuth.getToken()
        if(token.isNullOrEmpty()){
            goToLogin()
        }
    }

    protected fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        // Limpiar back stack para que no se pueda regresar con "back"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
