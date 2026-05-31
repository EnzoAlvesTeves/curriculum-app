package com.example.curriculumapp.client.externo

import com.example.curriculumapp.client.externo.dto.ViaCepResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepApi {
    @GET("{cep}/json/")
    suspend fun buscarCep(@Path("cep") cep: String): ViaCepResponse
}
