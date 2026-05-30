package com.example.curriculumapp.client.candidato.dto

import java.io.Serializable

data class AlterarCandidatoRequest(
    val id: Long? = null,
    val idUsuario: Long? = null,
    val nome: String? = null,
    val email: String? = null,
    val sexo: String? = null,
    val telefone: String? = null,
    val dataNascimento: String? = null,
    val resumoProfissional: String? = null
) : Serializable
