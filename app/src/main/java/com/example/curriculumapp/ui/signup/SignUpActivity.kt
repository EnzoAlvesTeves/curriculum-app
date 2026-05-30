package com.example.curriculumapp.ui.signup

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.client.usuario.UsuarioClient
import com.example.curriculumapp.client.usuario.dto.CreateUsuarioRequest
import com.example.curriculumapp.databinding.ActivitySignUpBinding
import com.example.curriculumapp.model.TipoUsuario
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            validateAndSignUp()
        }

        binding.tvBackToLogin.setOnClickListener {
            finish()
        }
    }

    private fun validateAndSignUp() {
        val name = binding.etName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        
        val type = if (binding.rbCandidato.isChecked) TipoUsuario.CANDIDATO.name else TipoUsuario.RH.name

        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "As senhas não conferem", Toast.LENGTH_SHORT).show()
            return
        }

        val request = CreateUsuarioRequest(
            nome = name,
            sobrenome = lastName,
            email = email,
            telefone = phone.ifEmpty { null },
            tipo = type,
            senha = password
        )

        signUp(request)
    }

    private fun signUp(request: CreateUsuarioRequest) {
        lifecycleScope.launch {
            try {
                UsuarioClient.api.criar(request)
                Toast.makeText(this@SignUpActivity, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                finish() // Voltar para o login
            } catch (e: Exception) {
                Toast.makeText(this@SignUpActivity, "Erro ao cadastrar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
