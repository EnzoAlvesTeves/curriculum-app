package com.example.curriculumapp.client.usuario

import com.example.curriculumapp.client.usuario.dto.AlterarSenhaPorUsernameRequest
import com.example.curriculumapp.client.usuario.dto.AlterarSenhaRequest
import com.example.curriculumapp.client.usuario.dto.CreateUsuarioRequest
import com.example.curriculumapp.client.usuario.dto.UpdateUsuarioRequest
import com.example.curriculumapp.client.usuario.dto.UsuarioResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioApi {

    @POST("api/usuarios")
    suspend fun criar(@Body request: CreateUsuarioRequest): UsuarioResponse

    @GET("api/usuarios")
    suspend fun listar(): List<UsuarioResponse>

    @GET("api/usuarios/me")
    suspend fun getMe(): UsuarioResponse

    @GET("api/usuarios/{id}")
    suspend fun buscarPorId(@Path("id") id: Long): UsuarioResponse

    @PUT("api/usuarios/{id}")
    suspend fun alterar(@Path("id") id: Long, @Body request: UpdateUsuarioRequest): UsuarioResponse

    @DELETE("api/usuarios/{id}")
    suspend fun deletar(@Path("id") id: Long)

    @PATCH("api/usuarios/me/senha")
    suspend fun alterarSenha(@Body request: AlterarSenhaRequest)

    @PATCH("api/usuarios/senha/username")
    suspend fun alterarSenhaPorEmail(@Body request: AlterarSenhaPorUsernameRequest)

}
