package com.example.curriculumapp.client.usuario

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UsuarioClient {
    private const val BASE_URL = "http://10.0.2.2:8091/" // Ajustado para emulador Android

    val api: UsuarioApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(UsuarioApi::class.java)
    }
}