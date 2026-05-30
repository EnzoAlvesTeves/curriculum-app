package com.example.curriculumapp.client.vaga

import com.example.curriculumapp.client.vaga.dto.*
import retrofit2.http.*

interface EmpresaApi {

    @GET("api/empresas/{id}")
    suspend fun buscarPorId(@Path("id") id: Long): EmpresaResponse

    @PUT("api/empresas/{id}")
    suspend fun atualizar(@Path("id") id: Long, @Body request: UpdateEmpresaRequest): EmpresaResponse

    @DELETE("api/empresas/{id}")
    suspend fun deletar(@Path("id") id: Long)

    @GET("api/empresas")
    suspend fun listarTodas(): List<EmpresaResponse>

    @POST("api/empresas")
    suspend fun salvar(@Body request: CreateEmpresaRequest): EmpresaResponse
}
