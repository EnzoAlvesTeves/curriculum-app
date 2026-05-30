package com.example.curriculumapp.client.candidato.dto

import java.io.Serializable

data class UsuarioMeResponse(
    val id: Long? = null,
    val tipo: TipoUsuario? = null,
    val nome: String? = null,
    val sobrenome: String? = null,
    val email: String? = null
) : Serializable