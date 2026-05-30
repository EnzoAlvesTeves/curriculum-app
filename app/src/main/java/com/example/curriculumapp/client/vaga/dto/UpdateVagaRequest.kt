package com.example.curriculumapp.client.vaga.dto

import java.io.Serializable
import java.math.BigDecimal

data class UpdateVagaRequest(
    val titulo: String,
    val descricao: String,
    val salario: BigDecimal? = null,
    val beneficios: String? = null,
    val idEmpresa: Long
) : Serializable
