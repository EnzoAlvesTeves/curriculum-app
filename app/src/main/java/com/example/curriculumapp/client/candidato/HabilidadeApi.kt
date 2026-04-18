package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.CandidatoDTO
import retrofit2.http.*

interface HabilidadeApi {
    @PUT("/habilidades")
    suspend fun atualizar(@Body candidatoDTO: CandidatoDTO): CandidatoDTO

    @POST("/habilidades")
    suspend fun salvar(@Body candidatoDTO: CandidatoDTO): CandidatoDTO

    @GET("/habilidades/candidato/{candidatoId}")
    suspend fun buscarPorId(@Path("id") id: Int): CandidatoDTO

    @DELETE("/habilidades/{experienciaId}")
    suspend fun deletar(@Path("id") id: Int)
}