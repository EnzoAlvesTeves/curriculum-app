package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.util.NetworkConfig

object ExperienciaClient {
    val api: ExperienciaApi by lazy {
        NetworkConfig.buildRetrofit("ms-curriculum").create(ExperienciaApi::class.java)
    }
}
