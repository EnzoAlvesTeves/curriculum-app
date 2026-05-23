package com.example.curriculumapp.client.usuario.dto

import java.io.Serializable

data class UsuarioDTO(
    var id: Long? = null,
    val nome: String,
    val email: String,
    val senha: String
) : Serializable  {
    val isAdmin: Boolean get() = id == 1L
}