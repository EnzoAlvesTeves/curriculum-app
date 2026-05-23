package com.example.curriculumapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.client.usuario.UsuarioClient
import com.example.curriculumapp.client.usuario.dto.LoginDTO
import com.example.curriculumapp.client.usuario.dto.UsuarioDTO
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(this)

        // Verifica se já existe sessão ativa (Equivalente ao localStorage.getItem)
        val usuarioLogado = sessionManager.getSession()
        if (usuarioLogado != null) {
            irParaHome(usuarioLogado)
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListenerLogin()
        setupListenerRegister()
        setupListenerForgotPassword()
    }

    private fun setupListenerForgotPassword() {
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupListenerRegister() {
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupListenerLogin() {
        val btnEnter = findViewById<Button>(R.id.btnEnter)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        btnEnter.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val usuarioDTO = UsuarioClient.api.login(
                        LoginDTO(
                            email = email,
                            senha = password
                        )
                    )

                    Log.d("API_SUCCESS", "Usuário logado: $usuarioDTO")
                    sessionManager.saveSession(usuarioDTO)
                    irParaHome(usuarioDTO)
                } catch (e: Exception) {
                    Log.e("API_ERROR", "Erro no login: ${e.message}")
                    Toast.makeText(
                        this@LoginActivity,
                        "E-mail ou senha incorretos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun irParaHome(usuarioDTO: UsuarioDTO) {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        intent.putExtra("usuarioDTO", usuarioDTO)
        startActivity(intent)
        finish()
    }
}