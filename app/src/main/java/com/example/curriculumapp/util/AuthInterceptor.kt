package com.example.curriculumapp.util

import com.example.curriculumapp.CurriculumApplication
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Don't add token to login or refresh calls
        val path = originalRequest.url.encodedPath
        if (path.contains("api/auth/login") || path.contains("api/auth/refresh")) {
            return chain.proceed(originalRequest)
        }

        val token = CurriculumApplication.instance.tokenManager.getAccessToken()
        return if (token != null) {
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }
}
