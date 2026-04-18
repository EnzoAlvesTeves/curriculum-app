package com.example.curriculumapp.client.vaga

import com.example.curriculumapp.client.vaga.dto.CandidatoVagaDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CandidatoVagaApi {
    @POST("/candidato-vaga")
    suspend fun candidatar(@Body candidatoVagaDTO: CandidatoVagaDTO): CandidatoVagaDTO

    @GET("/candidato-vaga/vaga/{vagaId}")
    suspend fun listarCandidaturasPorVaga(@Path("vagaId") vagaId: Int): List<CandidatoVagaDTO>

    @GET("/candidato-vaga/candidato/{candidatoId}")
    suspend fun listarCandidaturasPorCandidato(@Path("candidatoId") candidatoId: Int): List<CandidatoVagaDTO>

    @DELETE("/candidato-vaga/{id}")
    suspend fun cancelarCandidatura(@Path("id") id: Int)
}