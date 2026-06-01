package com.example.curriculumapp.client.vaga

import com.example.curriculumapp.util.ClientManager
import com.example.curriculumapp.util.NetworkConfig

object EmpresaClient {
    val api: EmpresaApi
        get() = ClientManager.managedLazy("empresa_api") {
            NetworkConfig.buildRetrofit("ms-vagas").create(EmpresaApi::class.java)
        } as EmpresaApi
}
