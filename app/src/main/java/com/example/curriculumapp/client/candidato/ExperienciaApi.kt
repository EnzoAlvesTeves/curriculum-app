package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.CandidatoDTO
import retrofit2.http.*

interface ExperienciaApi {
    @PUT("/exeprencias")
    suspend fun atualizar(@Body candidatoDTO: CandidatoDTO): CandidatoDTO

    @POST("/exeprencias")
    suspend fun salvar(@Body candidatoDTO: CandidatoDTO): CandidatoDTO

    @GET("/experencias/candidato/{candidatoId}")
    suspend fun buscarPorId(@Path("id") id: Int): CandidatoDTO

    @DELETE("/experencias/{experienciaId}")
    suspend fun deletar(@Path("id") id: Int)
}