package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.AlterarCandidatoRequest
import com.example.curriculumapp.client.candidato.dto.CandidatoDTO
import retrofit2.http.*

interface CandidatoApi {

    @PUT("api/candidatos")
    suspend fun atualizar(@Body request: AlterarCandidatoRequest): CandidatoDTO

    @POST("api/candidatos")
    suspend fun salvar(@Body candidatoDTO: CandidatoDTO): CandidatoDTO

    @DELETE("api/candidatos")
    suspend fun deletar()

    @GET("api/candidatos/{candidatoId}")
    suspend fun buscarPorId(@Path("candidatoId") candidatoId: Long): CandidatoDTO
}
