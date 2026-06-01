package com.example.curriculumapp.client.vaga

import com.example.curriculumapp.client.usuario.dto.UsuarioResponse
import com.example.curriculumapp.client.vaga.dto.CandidaturaResponse
import com.example.curriculumapp.client.vaga.dto.CreateVagaRequest
import com.example.curriculumapp.client.vaga.dto.UpdateVagaRequest
import com.example.curriculumapp.client.vaga.dto.VagaResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VagaApi {

    @POST("api/vagas")
    suspend fun criar(@Body request: CreateVagaRequest): VagaResponse

    @GET("api/vagas")
    suspend fun listar(): List<VagaResponse>

    @PUT("api/vagas/{id}")
    suspend fun atualizar(@Path("id") id: Long, @Body request: UpdateVagaRequest): VagaResponse

    @DELETE("api/vagas/{id}")
    suspend fun deletar(@Path("id") id: Long)

    @GET("api/vagas/empresa/{idEmpresa}")
    suspend fun buscarPorEmpresa(@Path("idEmpresa") idEmpresa: Long): List<VagaResponse>

    @GET("api/vagas/minhas-criadas")
    suspend fun buscarMinhasVagasCriadas(): List<VagaResponse>

    @POST("api/vagas/{idVaga}/candidaturas")
    suspend fun candidatar(@Path("idVaga") idVaga: Long): CandidaturaResponse

    @DELETE("api/vagas/{idVaga}/candidaturas")
    suspend fun removerCandidatura(@Path("idVaga") idVaga: Long)

    @GET("api/vagas/minhas-candidaturas")
    suspend fun buscarMinhasCandidaturas(): List<VagaResponse>

    @GET("api/vagas/{idVaga}/candidatos")
    suspend fun listarCandidatos(@Path("idVaga") idVaga: Long): List<UsuarioResponse>
}
