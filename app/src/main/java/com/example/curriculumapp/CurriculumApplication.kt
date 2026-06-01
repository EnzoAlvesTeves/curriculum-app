package com.example.curriculumapp

import android.app.Application
import com.example.curriculumapp.util.TokenManager
import com.example.curriculumapp.util.UrlManager

class CurriculumApplication : Application() {

    lateinit var tokenManager: TokenManager
        private set
        
    lateinit var urlManager: UrlManager
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        tokenManager = TokenManager(applicationContext)
        urlManager = UrlManager(applicationContext)
    }

    companion object {
        lateinit var instance: CurriculumApplication
            private set
    }
}
