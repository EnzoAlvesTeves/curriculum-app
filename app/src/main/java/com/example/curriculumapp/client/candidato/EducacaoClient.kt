package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.util.ClientManager
import com.example.curriculumapp.util.NetworkConfig

object EducacaoClient {
    val api: EducacaoApi
        get() = ClientManager.managedLazy("educacao_api") {
            NetworkConfig.buildRetrofit("ms-curriculum").create(EducacaoApi::class.java)
        } as EducacaoApi
}
