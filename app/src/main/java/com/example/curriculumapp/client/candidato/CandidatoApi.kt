package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.CandidatoDTO
import retrofit2.http.*

interface CandidatoApi {
    @GET("/candidatos")
    suspend fun listarTodos(): List<CandidatoDTO>

    @POST("/candidatos")
    suspend fun salvar(@Body candidatoDTO: CandidatoDTO): CandidatoDTO

    @PUT("/candidatos")
    suspend fun atualizar(@Body candidatoDTO: CandidatoDTO): CandidatoDTO

    @GET("/candidatos/{id}")
    suspend fun buscarPorId(@Path("id") id: Int): CandidatoDTO

    @DELETE("/candidatos/{id}")
    suspend fun deletar(@Path("id") id: Int)

    @GET("/candidatos/usuario/{usuarioId}")
    suspend fun buscarPorUsuario(@Path("usuarioId") usuarioId: Int): CandidatoDTO
}