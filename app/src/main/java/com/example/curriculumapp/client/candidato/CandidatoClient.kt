package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.util.NetworkConfig

object CandidatoClient {
    val api: CandidatoApi by lazy {
        NetworkConfig.buildRetrofit("ms-curriculum").create(CandidatoApi::class.java)
    }
}
