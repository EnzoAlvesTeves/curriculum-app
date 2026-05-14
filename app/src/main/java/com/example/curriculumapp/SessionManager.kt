package com.example.curriculumapp

import android.content.Context
import android.content.SharedPreferences
import com.example.curriculumapp.client.usuario.dto.UsuarioDTO
import com.google.gson.Gson

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("curriculum_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveSession(usuario: UsuarioDTO) {
        val json = gson.toJson(usuario)
        prefs.edit().putString("usuario_session", json).apply()
    }

    fun getSession(): UsuarioDTO? {
        val json = prefs.getString("usuario_session", null) ?: return null
        return gson.fromJson(json, UsuarioDTO::class.java)
    }

    fun logout() {
        prefs.edit().remove("usuario_session").apply()
    }

    fun isAdmin(): Boolean {
        return getSession()?.isAdmin ?: false
    }
}