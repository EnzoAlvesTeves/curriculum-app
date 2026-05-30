package com.example.curriculumapp.client.vaga.dto

import java.io.Serializable

data class CandidaturaResponse(
    val id: Long? = null,
    val idUsuario: Long? = null,
    val idVaga: Long? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
) : Serializable
