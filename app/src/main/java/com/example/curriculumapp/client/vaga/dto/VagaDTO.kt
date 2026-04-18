package com.example.curriculumapp.client.vaga.dto

import java.io.Serializable

data class VagaDTO(
    val id: Int? = null,
    val titulo: String,
    val descricao: String,
    val empresa: String,
    val beneficios: String,
    val salario: Double
) : Serializable