package com.example.curriculumapp.client.usuario.dto

import java.io.Serializable

data class AuthTokenResponse(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val expiresIn: Long? = null,
    val refreshExpiresIn: Long? = null,
    val tokenType: String? = null,
    val scope: String? = null
) : Serializable
