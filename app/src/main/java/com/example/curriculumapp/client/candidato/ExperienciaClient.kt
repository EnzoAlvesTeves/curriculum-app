package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.util.ClientManager
import com.example.curriculumapp.util.NetworkConfig

object ExperienciaClient {
    val api: ExperienciaApi
        get() = ClientManager.managedLazy("experiencia_api") {
            NetworkConfig.buildRetrofit("ms-curriculum").create(ExperienciaApi::class.java)
        } as ExperienciaApi
}
