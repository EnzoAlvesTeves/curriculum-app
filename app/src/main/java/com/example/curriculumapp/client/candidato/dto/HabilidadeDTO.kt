package com.example.curriculumapp.client.candidato.dto

import java.io.Serializable

data class HabilidadeDTO(
    val id: Int? = null,
    val descricao: String,
    val nivel: String,
    val especialidade: String,
    val candidatoId: Int? = null
) : Serializable