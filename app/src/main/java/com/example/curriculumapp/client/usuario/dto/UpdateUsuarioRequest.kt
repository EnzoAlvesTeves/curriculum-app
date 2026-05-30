package com.example.curriculumapp.client.usuario.dto

import java.io.Serializable

data class UpdateUsuarioRequest(
    val nome: String,
    val sobrenome: String,
    val telefone: String? = null,
    val tipo: String
) : Serializable
