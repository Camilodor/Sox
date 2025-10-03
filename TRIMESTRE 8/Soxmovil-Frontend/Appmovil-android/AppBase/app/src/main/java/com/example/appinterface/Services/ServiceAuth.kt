package com.example.appinterface.Services

import android.content.Context
import android.content.SharedPreferences
import com.example.appinterface.Api.ApiResponse
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Modelos.Login.LoginRequest
import com.example.appinterface.Modelos.Login.LoginResponse
import com.example.appinterface.Modelos.Login.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceAuth(private val context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private fun saveToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    fun getToken(): String? = prefs.getString("jwt_token", null)

    fun clearToken() {
        prefs.edit().remove("jwt_token").apply()
    }

    fun login(login: String, contrasena: String, callback: (Boolean, String?, User?) -> Unit) {
        val request = LoginRequest(login, contrasena)
        RetrofitInstance.api.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        saveToken(it.access_token)
                        callback(true, null, it.user)
                    } ?: callback(false, "Respuesta vacía", null)
                } else callback(false, "Credenciales inválidas", null)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(false, t.message, null)
            }
        })
    }

    fun me(callback: (Boolean, String?, User?) -> Unit) {
        val token = getToken() ?: run { callback(false, "No hay token", null); return }

        RetrofitInstance.api.me("Bearer $token").enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful) callback(true, null, response.body())
                else callback(false, "Error al obtener usuario", null)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(false, t.message, null)
            }
        })
    }

    fun logout(callback: (Boolean, String?) -> Unit) {
        val token = getToken() ?: run { callback(false, "No hay token"); return }

        RetrofitInstance.api.logout("Bearer $token").enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if(response.isSuccessful){
                    clearToken()
                    callback(true, null)
                } else callback(false, "Error al cerrar sesión")
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                callback(false, t.message)
            }
        })
    }
}

