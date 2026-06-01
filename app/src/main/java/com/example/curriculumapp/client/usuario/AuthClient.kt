package com.example.curriculumapp.client.usuario

import com.example.curriculumapp.util.NetworkConfig

object AuthClient {
    val api: AuthApi by lazy {
        NetworkConfig.buildRetrofit("ms-usuarios").create(AuthApi::class.java)
    }
}
