package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.AlterarCandidatoRequest
import com.example.curriculumapp.client.candidato.dto.CandidatoDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CandidatoApi {

    @PUT("api/candidatos")
    suspend fun alterar(@Body request: AlterarCandidatoRequest): CandidatoDTO

    @POST("api/candidatos")
    suspend fun criar(@Body candidatoDTO: CandidatoDTO): CandidatoDTO

    @GET("api/candidatos/{candidatoId}")
    suspend fun buscarPorId(@Path("candidatoId") candidatoId: Long): CandidatoDTO

    @GET("api/candidatos/me")
    suspend fun me(): CandidatoDTO

    @DELETE("api/candidatos")
    suspend fun deletar()
}
