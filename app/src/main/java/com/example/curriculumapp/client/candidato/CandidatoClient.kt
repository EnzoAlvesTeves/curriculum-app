package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.util.ClientManager
import com.example.curriculumapp.util.NetworkConfig

object CandidatoClient {
    val api: CandidatoApi
        get() = ClientManager.managedLazy("candidato_api") {
            NetworkConfig.buildRetrofit("ms-curriculum").create(CandidatoApi::class.java)
        } as CandidatoApi
}
