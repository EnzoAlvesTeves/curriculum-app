package com.example.curriculumapp.client.vaga.dto

import java.io.Serializable

data class EmpresaResponse(
    val id: Long? = null,
    val nome: String? = null,
    val estado: String? = null,
    val cidade: String? = null,
    val bairro: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
) : Serializable
