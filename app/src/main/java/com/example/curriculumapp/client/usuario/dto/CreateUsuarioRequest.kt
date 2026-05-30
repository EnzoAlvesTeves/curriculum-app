package com.example.curriculumapp.client.usuario.dto

import java.io.Serializable

data class CreateUsuarioRequest(
    val nome: String,
    val sobrenome: String,
    val email: String,
    val telefone: String? = null,
    val tipo: String,
    val senha: String
) : Serializable
