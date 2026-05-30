package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.ExperienciaDTO
import retrofit2.http.*

interface ExperienciaApi {

    @PUT("api/candidatos/{candidatoId}/experiencias/{experienciaId}")
    suspend fun atualizar(
        @Path("candidatoId") candidatoId: Long,
        @Path("experienciaId") experienciaId: Long,
        @Body experienciaDTO: ExperienciaDTO
    ): ExperienciaDTO

    @DELETE("api/candidatos/{candidatoId}/experiencias/{experienciaId}")
    suspend fun deletar(
        @Path("candidatoId") candidatoId: Long,
        @Path("experienciaId") experienciaId: Long
    )

    @GET("api/candidatos/{candidatoId}/experiencias")
    suspend fun listar(@Path("candidatoId") candidatoId: Long): List<ExperienciaDTO>

    @POST("api/candidatos/{candidatoId}/experiencias")
    suspend fun salvar(
        @Path("candidatoId") candidatoId: Long,
        @Body experienciaDTO: ExperienciaDTO
    ): ExperienciaDTO
}
