package com.example.curriculumapp.client.externo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ViaCepClient {
    private const val BASE_URL = "https://viacep.com.br/ws/"

    val api: ViaCepApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ViaCepApi::class.java)
    }
}
