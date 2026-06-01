package com.example.curriculumapp.client.usuario

import com.example.curriculumapp.util.ClientManager
import com.example.curriculumapp.util.NetworkConfig

object UsuarioClient {
    val api: UsuarioApi
        get() = ClientManager.managedLazy("usuario_api") {
            NetworkConfig.buildRetrofit("ms-usuarios").create(UsuarioApi::class.java)
        } as UsuarioApi
}
