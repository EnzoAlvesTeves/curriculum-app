package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.HabilidadeDTO
import retrofit2.http.*

interface HabilidadeApi {

    @PUT("api/candidatos/{candidatoId}/habilidades/{habilidadeId}")
    suspend fun atualizar(
        @Path("candidatoId") candidatoId: Long,
        @Path("habilidadeId") habilidadeId: Long,
        @Body habilidadeDTO: HabilidadeDTO
    ): HabilidadeDTO

    @DELETE("api/candidatos/{candidatoId}/habilidades/{habilidadeId}")
    suspend fun deletar(
        @Path("candidatoId") candidatoId: Long,
        @Path("habilidadeId") habilidadeId: Long
    )

    @GET("api/candidatos/{candidatoId}/habilidades")
    suspend fun listar(@Path("candidatoId") candidatoId: Long): List<HabilidadeDTO>

    @POST("api/candidatos/{candidatoId}/habilidades")
    suspend fun salvar(
        @Path("candidatoId") candidatoId: Long,
        @Body habilidadeDTO: HabilidadeDTO
    ): HabilidadeDTO
}
