package com.example.curriculumapp.client.vaga

import com.example.curriculumapp.client.vaga.dto.*
import retrofit2.http.*

interface VagaApi {

    @GET("api/vagas")
    suspend fun listarTodas(): List<VagaResponse>

    @POST("api/vagas")
    suspend fun salvar(@Body request: CreateVagaRequest): VagaResponse

    @PUT("api/vagas/{id}")
    suspend fun atualizar(@Path("id") id: Long, @Body request: UpdateVagaRequest): VagaResponse

    @GET("api/vagas/{id}")
    suspend fun buscarPorId(@Path("id") id: Long): VagaResponse

    @DELETE("api/vagas/{id}")
    suspend fun deletar(@Path("id") id: Long)

    @POST("api/vagas/{idVaga}/candidaturas")
    suspend fun candidatar(@Path("idVaga") idVaga: Long): CandidaturaResponse

    @DELETE("api/vagas/{idVaga}/candidaturas")
    suspend fun removerCandidatura(@Path("idVaga") idVaga: Long)

    @GET("api/vagas/minhas-criadas")
    suspend fun listarMinhasVagasCriadas(): List<VagaResponse>

    @GET("api/vagas/minhas-candidaturas")
    suspend fun listarMinhasCandidaturas(): List<VagaResponse>

    @GET("api/vagas/empresa/{idEmpresa}")
    suspend fun listarVagasPorEmpresa(@Path("idEmpresa") idEmpresa: Long): List<VagaResponse>
}
