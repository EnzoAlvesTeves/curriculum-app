package com.example.curriculumapp.client.vaga.dto

import java.io.Serializable

data class UpdateEmpresaRequest(
    val nome: String,
    val estado: String,
    val cidade: String,
    val bairro: String? = null
) : Serializable
