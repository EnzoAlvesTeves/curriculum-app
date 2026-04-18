package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.CandidatoDTO
import retrofit2.http.*

interface EnderecoApi {
    @PUT("/enderecos")
    suspend fun atualizar(@Body candidatoDTO: CandidatoDTO): CandidatoDTO

    @POST("/enderecos")
    suspend fun salvar(@Body candidatoDTO: CandidatoDTO): CandidatoDTO

    @GET("/enderecos/{id}")
    suspend fun buscarPorId(@Path("id") id: Int): CandidatoDTO

    @DELETE("/enderecos/{enderecoId}")
    suspend fun deletar(@Path("id") id: Int)
}