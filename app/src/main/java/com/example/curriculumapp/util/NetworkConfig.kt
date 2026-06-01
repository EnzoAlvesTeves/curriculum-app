package com.example.curriculumapp.util

import com.example.curriculumapp.CurriculumApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkConfig {
    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .authenticator(TokenAuthenticator())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun buildRetrofit(servicePath: String): Retrofit {
        val host = CurriculumApplication.instance.urlManager.getBaseHost()
        val baseUrl = if (servicePath.startsWith("/")) {
            "$host$servicePath"
        } else {
            "$host/$servicePath"
        }
        
        // Retrofit requires base URL to end with /
        val finalUrl = if (baseUrl.endsWith("/")) baseUrl else "$baseUrl/"

        return Retrofit.Builder()
            .baseUrl(finalUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
