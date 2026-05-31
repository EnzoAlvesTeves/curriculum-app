package com.example.curriculumapp.ui.empresa

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.R
import com.example.curriculumapp.client.vaga.EmpresaClient
import com.example.curriculumapp.client.vaga.dto.CreateEmpresaRequest
import com.example.curriculumapp.databinding.ActivityCreateEmpresaBinding
import com.example.curriculumapp.ui.base.BaseActivity
import kotlinx.coroutines.launch

class CreateEmpresaActivity : BaseActivity() {

    private lateinit var binding: ActivityCreateEmpresaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEmpresaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader(binding.header)
        setupDrawer(binding.drawerLayout, binding.navigationView)

        binding.btnSalvar.setOnClickListener {
            validateAndSave()
        }
    }

    private fun validateAndSave() {
        val nome = binding.etNome.text.toString()
        val estado = binding.etEstado.text.toString()
        val cidade = binding.etCidade.text.toString()
        val bairro = binding.etBairro.text.toString()

        if (nome.isEmpty() || estado.isEmpty() || cidade.isEmpty()) {
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        val request = CreateEmpresaRequest(
            nome = nome,
            estado = estado,
            cidade = cidade,
            bairro = bairro.ifEmpty { null }
        )

        saveEmpresa(request)
    }

    private fun saveEmpresa(request: CreateEmpresaRequest) {
        lifecycleScope.launch {
            try {
                EmpresaClient.api.criar(request)
                Toast.makeText(this@CreateEmpresaActivity, R.string.empresa_cadastrada_sucesso, Toast.LENGTH_SHORT).show()
                finish() // Voltar para a tela anterior (ou listagem)
            } catch (e: Exception) {
                Toast.makeText(this@CreateEmpresaActivity, "Erro ao cadastrar empresa: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
