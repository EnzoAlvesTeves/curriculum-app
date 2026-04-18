package com.example.curriculumapp.client.candidato.dto

import java.io.Serializable

data class CandidatoDTO(
    val id: Int? = null,
    val nome: String,
    val email: String,
    val telefone: String,
    val sexo: String,
    val dataNascimento: String,
    val resumoProfissional: String,
    val usuarioId: Int? = null,
    val endereco: EnderecoDTO? = null,
    val educacoes: List<EducacaoDTO> = emptyList(),
    val experiencias: List<ExperienciaDTO> = emptyList(),
    val habilidades: List<HabilidadeDTO> = emptyList()
) : Serializable