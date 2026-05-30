package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.EducacaoDTO
import retrofit2.http.*

interface EducacaoApi {

    @PUT("api/candidatos/{candidatoId}/educacoes/{educacaoId}")
    suspend fun atualizar(
        @Path("candidatoId") candidatoId: Long,
        @Path("educacaoId") educacaoId: Long,
        @Body educacaoDTO: EducacaoDTO
    ): EducacaoDTO

    @DELETE("api/candidatos/{candidatoId}/educacoes/{educacaoId}")
    suspend fun deletar(
        @Path("candidatoId") candidatoId: Long,
        @Path("educacaoId") educacaoId: Long
    )

    @GET("api/candidatos/{candidatoId}/educacoes")
    suspend fun listar(@Path("candidatoId") candidatoId: Long): List<EducacaoDTO>

    @POST("api/candidatos/{candidatoId}/educacoes")
    suspend fun salvar(
        @Path("candidatoId") candidatoId: Long,
        @Body educacaoDTO: EducacaoDTO
    ): EducacaoDTO
}
