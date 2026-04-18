package com.example.curriculumapp.client.vaga.dto

import java.io.Serializable

data class CandidatoVagaDTO(
    val id: Int? = null,
    val candidatoId: Int,
    val vagaId: Int
) : Serializable