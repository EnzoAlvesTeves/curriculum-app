package com.example.curriculumapp.client.candidato.dto

import java.io.Serializable

data class ExperienciaDTO(
    val id: Long? = null,
    val idCandidato: Long? = null,
    val cargo: String? = null,
    val empresa: String? = null,
    val resumo: String? = null,
    val dataInicio: String? = null,
    val dataFim: String? = null
) : Serializable
