package com.example.curriculumapp.client.vaga

import com.example.curriculumapp.util.NetworkConfig

object VagaClient {
    val api: VagaApi by lazy {
        NetworkConfig.buildRetrofit("ms-vagas").create(VagaApi::class.java)
    }
}
