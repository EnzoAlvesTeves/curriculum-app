package com.example.curriculumapp.client.usuario.dto

import java.io.Serializable

data class AlterarSenhaPorUsernameRequest(
    val username: String,
    val novaSenha: String
) : Serializable
