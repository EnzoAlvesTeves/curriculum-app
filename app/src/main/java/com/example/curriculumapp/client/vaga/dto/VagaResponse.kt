package com.example.curriculumapp.client.vaga.dto

import java.io.Serializable
import java.math.BigDecimal

data class VagaResponse(
    val id: Long? = null,
    val titulo: String? = null,
    val descricao: String? = null,
    val salario: BigDecimal? = null,
    val beneficios: String? = null,
    val idEmpresa: Long? = null,
    val createdBy: Long? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
) : Serializable
