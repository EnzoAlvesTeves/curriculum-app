package com.example.curriculumapp.client.usuario

import com.example.curriculumapp.client.usuario.dto.AuthLoginRequest
import com.example.curriculumapp.client.usuario.dto.AuthRefreshTokenRequest
import com.example.curriculumapp.client.usuario.dto.AuthTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/login")
    suspend fun login(@Body request: AuthLoginRequest): AuthTokenResponse

    @POST("api/auth/refresh")
    suspend fun refresh(@Body request: AuthRefreshTokenRequest): AuthTokenResponse

    @POST("api/auth/refresh")
    fun refreshSync(@Body request: AuthRefreshTokenRequest): retrofit2.Call<AuthTokenResponse>
}
