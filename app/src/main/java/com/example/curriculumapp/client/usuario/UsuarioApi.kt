package com.example.curriculumapp.client.usuario

import com.example.curriculumapp.client.usuario.dto.LoginDTO
import com.example.curriculumapp.client.usuario.dto.UsuarioDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioApi {
    @PUT("/usuarios")
    suspend fun update(@Body usuarioDTO: UsuarioDTO): UsuarioDTO

    @POST("/usuarios")
    suspend fun create(@Body usuarioDTO: UsuarioDTO): UsuarioDTO

    @POST("/usuarios/login")
    suspend fun login(@Body loginDTO: LoginDTO): UsuarioDTO

    @GET("/usuarios/{id}")
    suspend fun findById(@Path("id") id: Long): UsuarioDTO

    @DELETE("/usuarios/{id}")
    suspend fun delete(@Path("id") id: Long)

    @GET("/usuarios/email/{email}")
    suspend fun findAll(): List<UsuarioDTO>
}