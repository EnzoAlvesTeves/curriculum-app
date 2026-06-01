package com.example.curriculumapp.client.vaga

import com.example.curriculumapp.util.NetworkConfig

object EmpresaClient {
    val api: EmpresaApi by lazy {
        NetworkConfig.buildRetrofit("ms-vagas").create(EmpresaApi::class.java)
    }
}
