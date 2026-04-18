package com.example.curriculumapp.client.usuario.dto

import java.io.Serializable

data class UsuarioDTO(
    val id: Long? = null,
    val nome: String,
    val email: String,
    val senha: String
) : Serializable