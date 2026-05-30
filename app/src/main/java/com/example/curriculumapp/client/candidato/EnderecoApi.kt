package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.client.candidato.dto.EnderecoDTO
import retrofit2.http.*

interface EnderecoApi {

    @GET("api/candidatos/{candidatoId}/endereco")
    suspend fun buscar(@Path("candidatoId") candidatoId: Long): EnderecoDTO

    @PUT("api/candidatos/{candidatoId}/endereco")
    suspend fun atualizar(
        @Path("candidatoId") candidatoId: Long,
        @Body enderecoDTO: EnderecoDTO
    ): EnderecoDTO

    @POST("api/candidatos/{candidatoId}/endereco")
    suspend fun salvar(
        @Path("candidatoId") candidatoId: Long,
        @Body enderecoDTO: EnderecoDTO
    ): EnderecoDTO

    @DELETE("api/candidatos/{candidatoId}/endereco")
    suspend fun deletar(@Path("candidatoId") candidatoId: Long)
}
