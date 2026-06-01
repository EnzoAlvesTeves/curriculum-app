package com.example.curriculumapp.client.usuario

import com.example.curriculumapp.util.NetworkConfig

object UsuarioClient {
    val api: UsuarioApi by lazy {
        NetworkConfig.buildRetrofit("ms-usuarios").create(UsuarioApi::class.java)
    }
}
