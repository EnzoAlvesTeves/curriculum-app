package com.example.curriculumapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.CurriculumApplication
import com.example.curriculumapp.MainActivity
import com.example.curriculumapp.client.usuario.AuthClient
import com.example.curriculumapp.client.usuario.dto.AuthLoginRequest
import com.example.curriculumapp.databinding.ActivityLoginBinding
import com.example.curriculumapp.ui.forgotpassword.ForgotPasswordActivity
import com.example.curriculumapp.ui.signup.SignUpActivity
import com.example.curriculumapp.util.TokenManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val tokenManager: TokenManager by lazy { CurriculumApplication.instance.tokenManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if already logged in
        if (tokenManager.getAccessToken() != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.btnSettings.setOnClickListener {
            showUrlSettingsDialog()
        }
    }

    private fun showUrlSettingsDialog() {
        val urlManager = CurriculumApplication.instance.urlManager
        val input = EditText(this)
        input.setText(urlManager.getBaseHost())
        input.setHint("Ex: http://ngrok-url.app")
        
        val padding = (16 * resources.displayMetrics.density).toInt()
        input.setPadding(padding, padding, padding, padding)

        AlertDialog.Builder(this)
            .setTitle("Configurar Base URL")
            .setMessage("Digite a URL base do seu servidor (ngrok):")
            .setView(input)
            .setPositiveButton("Salvar") { _, _ ->
                val newUrl = input.text.toString().trim()
                if (newUrl.isNotEmpty()) {
                    urlManager.saveBaseHost(newUrl)
                    Toast.makeText(this, "URL atualizada! Reiniciando...", Toast.LENGTH_SHORT).show()
                    
                    // Restart to apply lazy client changes
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    Runtime.getRuntime().exit(0)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun login(email: String, password: String) {
        binding.loading.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val request = AuthLoginRequest(username = email, senha = password)
                val response = AuthClient.api.login(request)

                if (response.accessToken != null) {
                    tokenManager.saveTokens(response.accessToken, response.refreshToken)
                    Toast.makeText(this@LoginActivity, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Erro ao realizar login", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.loading.visibility = View.GONE
            }
        }
    }
}
