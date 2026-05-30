package com.example.curriculumapp.client.usuario

import com.example.curriculumapp.util.NetworkConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UsuarioClient {
    private const val BASE_URL = "http://10.0.2.2:8000/ms-usuarios/"

    val api: UsuarioApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(NetworkConfig.okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(UsuarioApi::class.java)
    }
}