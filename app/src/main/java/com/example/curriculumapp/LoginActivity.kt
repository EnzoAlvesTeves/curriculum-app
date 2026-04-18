package com.example.curriculumapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.client.usuario.UsuarioClient
import com.example.curriculumapp.client.usuario.dto.LoginDTO
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnEnter = findViewById<Button>(R.id.btnEnter)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        btnEnter.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val usuarioDTO = UsuarioClient.api.login(LoginDTO(
                        email = etEmail.text.toString(),
                        senha = etPassword.text.toString()
                    ))

                    Log.d("API_SUCCESS", "Usuário logado: $usuarioDTO")
                    
                    // Se o login der certo, vai para a Home
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    intent.putExtra("usuarioDTO", usuarioDTO)

                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("API_ERROR", "Erro no login: ${e.message}")
                }
            }
        }
    }
}