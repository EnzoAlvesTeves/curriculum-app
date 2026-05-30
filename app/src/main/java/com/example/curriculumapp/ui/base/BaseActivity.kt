package com.example.curriculumapp.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.CurriculumApplication
import com.example.curriculumapp.client.usuario.UsuarioClient
import com.example.curriculumapp.databinding.IncludeHeaderBinding
import com.example.curriculumapp.ui.login.LoginActivity
import com.example.curriculumapp.ui.profile.ProfileActivity
import kotlinx.coroutines.launch

abstract class BaseActivity : AppCompatActivity() {

    protected var headerBinding: IncludeHeaderBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setupHeader(binding: IncludeHeaderBinding) {
        headerBinding = binding

        headerBinding?.btnHeaderLogout?.setOnClickListener {
            logout()
        }

        headerBinding?.btnHeaderProfile?.setOnClickListener {
            if (this.javaClass.simpleName != "ProfileActivity") {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
        }

        loadHeaderData()
    }

    protected fun loadHeaderData() {
        lifecycleScope.launch {
            try {
                val user = UsuarioClient.api.getMe()
                headerBinding?.let {
                    it.tvHeaderName.text = user.nome
                    it.tvHeaderEmail.text = user.email
                }
            } catch (e: Exception) {
                // If it fails, maybe token expired and refresh failed
            }
        }
    }

    protected fun logout() {
        CurriculumApplication.instance.tokenManager.clearTokens()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
