package com.example.curriculumapp.client.usuario.dto

import java.io.Serializable

data class AuthLoginRequest(
    val username: String,
    val senha: String
) : Serializable
