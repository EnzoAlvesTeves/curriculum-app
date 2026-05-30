package com.example.curriculumapp.util

import com.example.curriculumapp.CurriculumApplication
import com.example.curriculumapp.client.usuario.AuthApi
import com.example.curriculumapp.client.usuario.dto.AuthRefreshTokenRequest
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

            val authApi = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/ms-usuarios/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthApi::class.java)

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
