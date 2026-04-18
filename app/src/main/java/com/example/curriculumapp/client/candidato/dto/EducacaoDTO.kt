package com.example.curriculumapp.client.candidato.dto

import java.io.Serializable

data class EducacaoDTO(
    val id: Int? = null,
    val grau: String,
    val dataInicio: String,
    val dataFim: String,
    val instituicao: String,
    val curso: String,
    val candidatoId: Int? = null
) : Serializable