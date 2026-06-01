package com.example.curriculumapp.util

import com.example.curriculumapp.CurriculumApplication
import com.example.curriculumapp.client.usuario.AuthApi
import com.example.curriculumapp.client.usuario.dto.AuthRefreshTokenRequest
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val tokenManager = CurriculumApplication.instance.tokenManager
        val refreshToken = tokenManager.getRefreshToken() ?: return null

        synchronized(this) {
            val currentToken = tokenManager.getAccessToken()
            val requestToken = response.request.header("Authorization")?.replace("Bearer ", "")

            if (requestToken != currentToken) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $currentToken")
                    .build()
            }

            // Using buildRetrofit to get the dynamic URL for refresh
            val authApi = NetworkConfig.buildRetrofit("ms-usuarios").create(AuthApi::class.java)

            val refreshCall = authApi.refreshSync(AuthRefreshTokenRequest(refreshToken))
            val result = try {
                refreshCall.execute()
            } catch (e: Exception) {
                null
            }

            return if (result != null && result.isSuccessful && result.body() != null) {
                val newTokens = result.body()!!
                tokenManager.saveTokens(newTokens.accessToken!!, newTokens.refreshToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${newTokens.accessToken}")
                    .build()
            } else {
                tokenManager.clearTokens()
                null
            }
        }
    }
}
