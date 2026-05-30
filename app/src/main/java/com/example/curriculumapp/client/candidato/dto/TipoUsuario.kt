package com.example.curriculumapp.client.candidato.dto

enum class TipoUsuario {
    RH,
    CANDIDATO;

    fun ehRh(): Boolean {
        return this == RH
    }

    fun ehCandidato(): Boolean {
        return this == CANDIDATO
    }
}