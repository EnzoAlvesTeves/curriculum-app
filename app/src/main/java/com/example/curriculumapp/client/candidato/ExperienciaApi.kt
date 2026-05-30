package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.ExperienciaDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ExperienciaApi {

    @POST("api/candidatos/{candidatoId}/experiencias")
    suspend fun criar(
        @Path("candidatoId") candidatoId: Long,
        @Body experienciaDTO: ExperienciaDTO
    ): ExperienciaDTO

    @PUT("api/candidatos/{candidatoId}/experiencias/{experienciaId}")
    suspend fun alterar(
        @Path("candidatoId") candidatoId: Long,
        @Path("experienciaId") experienciaId: Long,
        @Body experienciaDTO: ExperienciaDTO
    ): ExperienciaDTO

    @GET("api/candidatos/{candidatoId}/experiencias")
    suspend fun buscar(@Path("candidatoId") candidatoId: Long): List<ExperienciaDTO>

    @DELETE("api/candidatos/{candidatoId}/experiencias/{experienciaId}")
    suspend fun deletar(
        @Path("candidatoId") candidatoId: Long,
        @Path("experienciaId") experienciaId: Long
    )

}
