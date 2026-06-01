package com.example.curriculumapp.util

import android.content.Context
import android.content.SharedPreferences

class UrlManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "url_prefs"
        private const val KEY_BASE_HOST = "base_host"
        private const val DEFAULT_HOST = "http://10.0.2.2:8000"
    }

    fun getBaseHost(): String {
        val host = prefs.getString(KEY_BASE_HOST, DEFAULT_HOST) ?: DEFAULT_HOST
        return if (host.endsWith("/")) host.removeSuffix("/") else host
    }

    fun saveBaseHost(host: String) {
        val sanitizedHost = if (host.endsWith("/")) host.removeSuffix("/") else host
        prefs.edit().putString(KEY_BASE_HOST, sanitizedHost).apply()
    }
}
