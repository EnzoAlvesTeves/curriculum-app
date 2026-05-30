package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.HabilidadeDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HabilidadeApi {

    @POST("api/candidatos/{candidatoId}/habilidades")
    suspend fun criar(
        @Path("candidatoId") candidatoId: Long,
        @Body habilidadeDTO: HabilidadeDTO
    ): HabilidadeDTO

    @PUT("api/candidatos/{candidatoId}/habilidades/{habilidadeId}")
    suspend fun alterar(
        @Path("candidatoId") candidatoId: Long,
        @Path("habilidadeId") habilidadeId: Long,
        @Body habilidadeDTO: HabilidadeDTO
    ): HabilidadeDTO

    @GET("api/candidatos/{candidatoId}/habilidades")
    suspend fun buscar(@Path("candidatoId") candidatoId: Long): List<HabilidadeDTO>

    @DELETE("api/candidatos/{candidatoId}/habilidades/{habilidadeId}")
    suspend fun deletar(
        @Path("candidatoId") candidatoId: Long,
        @Path("habilidadeId") habilidadeId: Long
    )

}
