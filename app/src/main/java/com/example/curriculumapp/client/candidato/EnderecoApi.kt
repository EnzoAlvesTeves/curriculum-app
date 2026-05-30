package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.EnderecoDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EnderecoApi {

    @POST("api/candidatos/{candidatoId}/endereco")
    suspend fun criar(
        @Path("candidatoId") candidatoId: Long,
        @Body enderecoDTO: EnderecoDTO
    ): EnderecoDTO

    @PUT("api/candidatos/{candidatoId}/endereco")
    suspend fun alterar(
        @Path("candidatoId") candidatoId: Long,
        @Body enderecoDTO: EnderecoDTO
    ): EnderecoDTO

    @GET("api/candidatos/{candidatoId}/endereco")
    suspend fun buscar(@Path("candidatoId") candidatoId: Long): EnderecoDTO

    @DELETE("api/candidatos/{candidatoId}/endereco")
    suspend fun deletar(@Path("candidatoId") candidatoId: Long)
}
