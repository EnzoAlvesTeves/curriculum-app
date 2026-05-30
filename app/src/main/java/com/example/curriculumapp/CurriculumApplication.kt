package com.example.curriculumapp

import android.app.Application
import com.example.curriculumapp.util.TokenManager

class CurriculumApplication : Application() {

    lateinit var tokenManager: TokenManager
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        tokenManager = TokenManager(applicationContext)
    }

    companion object {
        lateinit var instance: CurriculumApplication
            private set
    }
}
