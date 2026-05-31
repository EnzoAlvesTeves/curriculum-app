package com.example.curriculumapp.ui.vaga

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.R
import com.example.curriculumapp.client.vaga.EmpresaClient
import com.example.curriculumapp.client.vaga.VagaClient
import com.example.curriculumapp.client.vaga.dto.CreateVagaRequest
import com.example.curriculumapp.client.vaga.dto.EmpresaResponse
import com.example.curriculumapp.databinding.ActivityCreateVagaBinding
import com.example.curriculumapp.ui.base.BaseActivity
import kotlinx.coroutines.launch
import java.math.BigDecimal

class CreateVagaActivity : BaseActivity() {

    private lateinit var binding: ActivityCreateVagaBinding
    private var empresasList: List<EmpresaResponse> = emptyList()
    private var selectedEmpresaId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateVagaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader(binding.header)
        setupDrawer(binding.drawerLayout, binding.navigationView)

        loadEmpresas()

        binding.btnCadastrar.setOnClickListener {
            validateAndSave()
        }
    }

    private fun loadEmpresas() {
        lifecycleScope.launch {
            try {
                empresasList = EmpresaClient.api.listarTodas()
                val names = empresasList.map { it.nome ?: "Sem nome" }
                val adapter = ArrayAdapter(this@CreateVagaActivity, android.R.layout.simple_dropdown_item_1line, names)
                binding.spinnerEmpresa.setAdapter(adapter)

                binding.spinnerEmpresa.setOnItemClickListener { _, _, position, _ ->
                    selectedEmpresaId = empresasList[position].id
                }
            } catch (e: Exception) {
                Toast.makeText(this@CreateVagaActivity, "Erro ao carregar empresas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateAndSave() {
        val titulo = binding.etTitulo.text.toString()
        val descricao = binding.etDescricao.text.toString()
        val salarioStr = binding.etSalario.text.toString()
        val beneficios = binding.etBeneficios.text.toString()
        val empresaId = selectedEmpresaId

        if (empresaId == null) {
            Toast.makeText(this, "Selecione uma empresa", Toast.LENGTH_SHORT).show()
            return
        }

        if (titulo.isEmpty() || descricao.isEmpty()) {
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        val request = CreateVagaRequest(
            titulo = titulo,
            descricao = descricao,
            salario = if (salarioStr.isNotEmpty()) BigDecimal(salarioStr) else null,
            beneficios = beneficios.ifEmpty { null },
            idEmpresa = empresaId
        )

        saveVaga(request)
    }

    private fun saveVaga(request: CreateVagaRequest) {
        lifecycleScope.launch {
            try {
                VagaClient.api.criar(request)
                Toast.makeText(this@CreateVagaActivity, R.string.vaga_cadastrada_sucesso, Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@CreateVagaActivity, "Erro ao cadastrar vaga: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
