package com.example.curriculumapp.client.candidato.dto

import java.io.Serializable

data class EnderecoDTO(
    val id: Int? = null,
    val rua: String,
    val numero: String,
    val complemento: String,
    val cidade: String,
    val estado: String,
    val cep: String,
    val bairro: String,
) : Serializable