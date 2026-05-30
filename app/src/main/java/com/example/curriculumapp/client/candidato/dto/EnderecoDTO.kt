package com.example.curriculumapp.client.candidato.dto

import java.io.Serializable
import java.math.BigDecimal

data class EnderecoDTO(
    val id: Long? = null,
    val idCandidato: Long? = null,
    val rua: String? = null,
    val numero: String? = null,
    val complemento: String? = null,
    val cidade: String? = null,
    val estado: String? = null,
    val cep: String? = null,
    val bairro: String? = null,
    val latitude: BigDecimal? = null,
    val longitude: BigDecimal? = null
) : Serializable
