package com.example.curriculumapp.ui.forgotpassword

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.client.usuario.UsuarioClient
import com.example.curriculumapp.client.usuario.dto.AlterarSenhaPorUsernameRequest
import com.example.curriculumapp.databinding.ActivityForgotPasswordBinding
import kotlinx.coroutines.launch

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnChangePassword.setOnClickListener {
            validateAndChangePassword()
        }

        binding.tvBackToLogin.setOnClickListener {
            finish()
        }
    }

    private fun validateAndChangePassword() {
        val email = binding.etEmail.text.toString()
        val newPassword = binding.etNewPassword.text.toString()
        val confirmNewPassword = binding.etConfirmNewPassword.text.toString()

        if (email.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword != confirmNewPassword) {
            Toast.makeText(this, "As senhas não conferem", Toast.LENGTH_SHORT).show()
            return
        }

        val request = AlterarSenhaPorUsernameRequest(
            username = email,
            novaSenha = newPassword
        )

        changePassword(request)
    }

    private fun changePassword(request: AlterarSenhaPorUsernameRequest) {
        lifecycleScope.launch {
            try {
                UsuarioClient.api.alterarSenhaPorEmail(request)
                Toast.makeText(this@ForgotPasswordActivity, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@ForgotPasswordActivity, "Erro ao alterar senha: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
