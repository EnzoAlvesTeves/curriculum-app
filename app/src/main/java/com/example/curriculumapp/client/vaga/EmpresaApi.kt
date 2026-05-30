package com.example.curriculumapp.client.vaga

import com.example.curriculumapp.client.vaga.dto.CreateEmpresaRequest
import com.example.curriculumapp.client.vaga.dto.EmpresaResponse
import com.example.curriculumapp.client.vaga.dto.UpdateEmpresaRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EmpresaApi {

    @POST("api/empresas")
    suspend fun criar(@Body request: CreateEmpresaRequest): EmpresaResponse

    @GET("api/empresas")
    suspend fun listarTodas(): List<EmpresaResponse>

    @GET("api/empresas/{id}")
    suspend fun buscarPorId(@Path("id") id: Long): EmpresaResponse

    @PUT("api/empresas/{id}")
    suspend fun atualizar(@Path("id") id: Long, @Body request: UpdateEmpresaRequest): EmpresaResponse

    @DELETE("api/empresas/{id}")
    suspend fun deletar(@Path("id") id: Long)

}
