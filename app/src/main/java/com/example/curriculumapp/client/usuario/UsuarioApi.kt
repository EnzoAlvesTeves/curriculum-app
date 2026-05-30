package com.example.curriculumapp.client.usuario

import com.example.curriculumapp.client.usuario.dto.*
import retrofit2.http.*

interface UsuarioApi {

    @GET("api/usuarios/{id}")
    suspend fun findById(@Path("id") id: Long): UsuarioResponse

    @PUT("api/usuarios/{id}")
    suspend fun update(@Path("id") id: Long, @Body request: UpdateUsuarioRequest): UsuarioResponse

    @DELETE("api/usuarios/{id}")
    suspend fun delete(@Path("id") id: Long)

    @GET("api/usuarios")
    suspend fun findAll(): List<UsuarioResponse>

    @POST("api/usuarios")
    suspend fun create(@Body request: CreateUsuarioRequest): UsuarioResponse

    @PATCH("api/usuarios/senha/username")
    suspend fun alterarSenhaPorUsername(@Body request: AlterarSenhaPorUsernameRequest)

    @PATCH("api/usuarios/me/senha")
    suspend fun alterarPropriaSenha(@Body request: AlterarSenhaRequest)

    @GET("api/usuarios/me")
    suspend fun getMe(): UsuarioResponse
}
