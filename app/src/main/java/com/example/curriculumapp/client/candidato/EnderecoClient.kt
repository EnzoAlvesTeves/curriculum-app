package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.util.ClientManager
import com.example.curriculumapp.util.NetworkConfig

object EnderecoClient {
    val api: EnderecoApi
        get() = ClientManager.managedLazy("endereco_api") {
            NetworkConfig.buildRetrofit("ms-curriculum").create(EnderecoApi::class.java)
        } as EnderecoApi
}
