package com.example.curriculumapp.client.vaga

import com.example.curriculumapp.client.vaga.dto.VagaDTO
import retrofit2.http.*

interface VagaApi {
    @GET("/vagas")
    suspend fun listarTodas(): List<VagaDTO>

    @POST("/vagas")
    suspend fun salvar(@Body vagaDTO: VagaDTO): VagaDTO

    @PUT("/vagas")
    suspend fun atualizar(@Body vagaDTO: VagaDTO): VagaDTO

    @GET("/vagas/{id}")
    suspend fun buscarPorId(@Path("id") id: Int): VagaDTO

    @DELETE("/vagas/{id}")
    suspend fun deletar(@Path("id") id: Int)
}