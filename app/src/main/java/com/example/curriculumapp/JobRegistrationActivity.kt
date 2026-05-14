package com.example.curriculumapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.client.vaga.VagaClient
import com.example.curriculumapp.client.vaga.dto.VagaDTO
import com.example.curriculumapp.databinding.ActivityJobRegistrationBinding
import kotlinx.coroutines.launch

class JobRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnSaveJob.setOnClickListener {
            saveJob()
        }
    }

    private fun saveJob() {
        val titulo = binding.etJobTitle.text.toString()
        val descricao = binding.etDescription.text.toString()
        val empresa = binding.etCompany.text.toString()
        val beneficios = binding.etBenefits.text.toString()
        val salarioStr = binding.etSalary.text.toString()

        if (titulo.isEmpty() || descricao.isEmpty() || empresa.isEmpty() || salarioStr.isEmpty()) {
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        val salario = salarioStr.toDoubleOrNull() ?: 0.0

        val novaVaga = VagaDTO(
            titulo = titulo,
            descricao = descricao,
            empresa = empresa,
            beneficios = beneficios,
            salario = salario
        )

        lifecycleScope.launch {
            try {
                VagaClient.api.salvar(novaVaga)
                Toast.makeText(this@JobRegistrationActivity, "Vaga cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro ao salvar vaga: ${e.message}")
                Toast.makeText(this@JobRegistrationActivity, "Erro ao salvar vaga", Toast.LENGTH_SHORT).show()
            }
        }
    }
}