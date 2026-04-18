package com.example.curriculumapp.client.candidato

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExperienciaClient {
    private const val BASE_URL = "http://10.0.2.2:8091/"

    val api: ExperienciaApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(ExperienciaApi::class.java)
    }
}