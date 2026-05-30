package com.example.curriculumapp.client.usuario.dto

import java.io.Serializable

data class AuthRefreshTokenRequest(
    val refreshToken: String
) : Serializable
