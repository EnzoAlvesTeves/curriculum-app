package com.example.curriculumapp.client.candidato.dto

import java.io.Serializable

data class ExperienciaDTO(
    val id: Int? = null,
    val cargo: String,
    val empresa: String,
    val dataInicio: String,
    val dataFim: String,
    val candidatoId: Int? = null
) : Serializable