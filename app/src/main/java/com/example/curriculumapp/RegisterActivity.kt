package com.example.curriculumapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.client.usuario.UsuarioClient
import com.example.curriculumapp.client.usuario.dto.UsuarioDTO
import com.example.curriculumapp.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "As senhas não conferem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val novoUsuario = UsuarioDTO(
                        nome = name,
                        email = email,
                        senha = password
                    )
                    
                    val usuarioCriado = UsuarioClient.api.create(novoUsuario)
                    Log.d("API_SUCCESS", "Usuário criado: $usuarioCriado")
                    
                    Toast.makeText(this@RegisterActivity, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish() // Volta para a tela de Login
                } catch (e: Exception) {
                    Log.e("API_ERROR", "Erro ao cadastrar: ${e.message}")
                    Toast.makeText(this@RegisterActivity, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvLogin.setOnClickListener {
            finish() // Apenas fecha a tela para voltar ao Login
        }
    }
}