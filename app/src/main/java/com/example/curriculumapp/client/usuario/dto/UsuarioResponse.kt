package com.example.curriculumapp.client.usuario.dto

import java.io.Serializable

data class UsuarioResponse(
    val id: Long? = null,
    val keycloakUserId: String? = null,
    val nome: String? = null,
    val sobrenome: String? = null,
    val email: String? = null,
    val telefone: String? = null,
    val tipo: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
) : Serializable
