package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.EducacaoDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EducacaoApi {

    @POST("api/candidatos/{candidatoId}/educacoes")
    suspend fun criar(
        @Path("candidatoId") candidatoId: Long,
        @Body educacaoDTO: EducacaoDTO
    ): EducacaoDTO

    @PUT("api/candidatos/{candidatoId}/educacoes/{educacaoId}")
    suspend fun alterar(
        @Path("candidatoId") candidatoId: Long,
        @Path("educacaoId") educacaoId: Long,
        @Body educacaoDTO: EducacaoDTO
    ): EducacaoDTO

    @GET("api/candidatos/{candidatoId}/educacoes")
    suspend fun buscar(@Path("candidatoId") candidatoId: Long): List<EducacaoDTO>

    @DELETE("api/candidatos/{candidatoId}/educacoes/{educacaoId}")
    suspend fun deletar(
        @Path("candidatoId") candidatoId: Long,
        @Path("educacaoId") educacaoId: Long
    )
}
