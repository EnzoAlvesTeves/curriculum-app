package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.util.ClientManager
import com.example.curriculumapp.util.NetworkConfig

object HabilidadeClient {
    val api: HabilidadeApi
        get() = ClientManager.managedLazy("habilidade_api") {
            NetworkConfig.buildRetrofit("ms-curriculum").create(HabilidadeApi::class.java)
        } as HabilidadeApi
}
