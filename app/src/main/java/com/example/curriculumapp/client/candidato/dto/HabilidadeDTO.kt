package com.example.curriculumapp.client.candidato.dto

import java.io.Serializable

data class HabilidadeDTO(
    val id: Long? = null,
    val idCandidato: Long? = null,
    val descricao: String? = null,
    val nivel: String? = null
) : Serializable
