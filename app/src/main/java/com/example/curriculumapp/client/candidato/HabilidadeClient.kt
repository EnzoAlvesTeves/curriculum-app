package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.util.NetworkConfig

object HabilidadeClient {
    val api: HabilidadeApi by lazy {
        NetworkConfig.buildRetrofit("ms-curriculum").create(HabilidadeApi::class.java)
    }
}
