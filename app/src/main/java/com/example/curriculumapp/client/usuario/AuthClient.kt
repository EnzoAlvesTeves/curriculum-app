package com.example.curriculumapp.client.usuario

import com.example.curriculumapp.util.ClientManager
import com.example.curriculumapp.util.NetworkConfig

object AuthClient {
    val api: AuthApi
        get() = ClientManager.managedLazy("auth_api") {
            NetworkConfig.buildRetrofit("ms-usuarios").create(AuthApi::class.java)
        } as AuthApi
}
