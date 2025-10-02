package com.example.appinterface.Api


import com.example.appinterface.Api.Despachos.ApiDespachos
import com.example.appinterface.Api.Devoluciones.ApiDevoluciones
import com.example.appinterface.Api.Entregas.ApiEntregas
import com.example.appinterface.Api.Mercancias.ApiMercancias
import com.example.appinterface.Api.Proveedores.ApiProveedores
import com.example.appinterface.Api.Seguimientos.ApiSeguimientos
import com.example.appinterface.Api.TipoPago.ApiTipoPago
import com.example.appinterface.Api.Usuario.ApiUsuarios
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL_APIKOTLIN = "http://10.0.2.2:8080/"


    val apiUsuarios: ApiUsuarios by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_APIKOTLIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiUsuarios::class.java)
    }
    val apiProveedores: ApiProveedores by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_APIKOTLIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiProveedores::class.java)
    }

    val apiMercancias: ApiMercancias by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_APIKOTLIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiMercancias::class.java)
    }
    val apiDespachos: ApiDespachos by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_APIKOTLIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiDespachos::class.java)
    }
    val apiEntregas: ApiEntregas by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_APIKOTLIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiEntregas::class.java)
    }
    val apiDevoluciones: ApiDevoluciones by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_APIKOTLIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiDevoluciones::class.java)
    }
    val apiSeguimientos: ApiSeguimientos by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_APIKOTLIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiSeguimientos::class.java)
    }
    val apiTipoPago: ApiTipoPago by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_APIKOTLIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiTipoPago::class.java)
    }
}
