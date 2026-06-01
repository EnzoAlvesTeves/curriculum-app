package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.util.NetworkConfig

object EducacaoClient {
    val api: EducacaoApi by lazy {
        NetworkConfig.buildRetrofit("ms-curriculum").create(EducacaoApi::class.java)
    }
}
