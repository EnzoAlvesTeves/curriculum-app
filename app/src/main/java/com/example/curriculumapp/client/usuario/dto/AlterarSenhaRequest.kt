package com.example.curriculumapp.client.usuario.dto

import java.io.Serializable

data class AlterarSenhaRequest(
    val senhaAtual: String,
    val novaSenha: String
) : Serializable
