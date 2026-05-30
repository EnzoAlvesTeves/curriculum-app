package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.util.NetworkConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HabilidadeClient {
    private const val BASE_URL = "http://10.0.2.2:8000/ms-curriculum/"

    val api: HabilidadeApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(NetworkConfig.okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(HabilidadeApi::class.java)
    }
}