package com.example.curriculumapp.client.vaga

import com.example.curriculumapp.util.ClientManager
import com.example.curriculumapp.util.NetworkConfig

object VagaClient {
    val api: VagaApi
        get() = ClientManager.managedLazy("vaga_api") {
            NetworkConfig.buildRetrofit("ms-vagas").create(VagaApi::class.java)
        } as VagaApi
}
