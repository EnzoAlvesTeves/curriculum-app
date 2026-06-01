package com.example.curriculumapp.client.candidato

import com.example.curriculumapp.util.NetworkConfig

object EnderecoClient {
    val api: EnderecoApi by lazy {
        NetworkConfig.buildRetrofit("ms-curriculum").create(EnderecoApi::class.java)
    }
}
