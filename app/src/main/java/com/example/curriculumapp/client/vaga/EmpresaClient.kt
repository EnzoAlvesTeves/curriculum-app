package com.example.curriculumapp.client.vaga

import com.example.curriculumapp.util.NetworkConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EmpresaClient {
    private const val BASE_URL = "http://10.0.2.2:8000/ms-vagas/"

    val api: EmpresaApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(NetworkConfig.okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(EmpresaApi::class.java)
    }
}