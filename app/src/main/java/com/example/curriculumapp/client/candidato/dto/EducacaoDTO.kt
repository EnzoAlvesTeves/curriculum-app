package com.example.curriculumapp.client.candidato.dto

import java.io.Serializable

data class EducacaoDTO(
    val id: Long? = null,
    val idCandidato: Long? = null,
    val curso: String? = null,
    val grau: String? = null,
    val instituicao: String? = null,
    val dataInicio: String? = null,
    val dataFim: String? = null
) : Serializable
