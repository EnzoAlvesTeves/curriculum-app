package com.example.curriculumapp

data class Job(
    val id: Int = 0,
    val title: String,
    val candidatesCount: Int,
    val status: String // "Aberto" or "Fechado"
)