package com.example.curriculumapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBack = findViewById<LinearLayout>(R.id.btnBack)
        val btnSend = findViewById<Button>(R.id.btnSend)
        val etEmail = findViewById<EditText>(R.id.etEmail)

        btnBack.setOnClickListener {
            finish()
        }

        btnSend.setOnClickListener {
            val email = etEmail.text.toString()
            if (email.isNotEmpty()) {
                // Aqui você integraria com sua API
                Toast.makeText(this, "Instruções enviadas para $email", Toast.LENGTH_LONG).show()
                finish()
            } else {
                etEmail.error = "Por favor, insira seu e-mail"
            }
        }
    }
}