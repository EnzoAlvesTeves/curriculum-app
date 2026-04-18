package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.CandidatoDTO
import retrofit2.http.*

interface EducacaoApi {
    @PUT("/educacoes")
    suspend fun atualizar(@Body candidatoDTO: CandidatoDTO): CandidatoDTO

    @POST("/educacoes")
    suspend fun salvar(@Body candidatoDTO: CandidatoDTO): CandidatoDTO

    @GET("/educacoes/candidato/{candidatoId}")
    suspend fun buscarPorId(@Path("id") id: Int): CandidatoDTO

    @DELETE("/educacoes/{educacaoId}")
    suspend fun deletar(@Path("id") id: Int)
}