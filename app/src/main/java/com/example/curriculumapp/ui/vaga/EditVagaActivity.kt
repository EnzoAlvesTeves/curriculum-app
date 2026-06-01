package com.example.curriculumapp.ui.vaga

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.R
import com.example.curriculumapp.client.vaga.EmpresaClient
import com.example.curriculumapp.client.vaga.VagaClient
import com.example.curriculumapp.client.vaga.dto.EmpresaResponse
import com.example.curriculumapp.client.vaga.dto.UpdateVagaRequest
import com.example.curriculumapp.client.vaga.dto.VagaResponse
import com.example.curriculumapp.databinding.ActivityEditVagaBinding
import com.example.curriculumapp.ui.base.BaseActivity
import kotlinx.coroutines.launch
import java.math.BigDecimal

class EditVagaActivity : BaseActivity() {

    private lateinit var binding: ActivityEditVagaBinding
    private var vagaId: Long? = null
    private var empresasList: List<EmpresaResponse> = emptyList()
    private var selectedEmpresaId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditVagaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader(binding.header)
        setupDrawer(binding.drawerLayout, binding.navigationView)

        val vaga = intent.getSerializableExtra("VAGA_DATA") as? VagaResponse
        if (vaga != null) {
            vagaId = vaga.id
            preencherCampos(vaga)
        } else {
            finish()
        }

        binding.btnSalvar.setOnClickListener {
            validateAndSave()
        }

        binding.btnDeletar.setOnClickListener {
            showDeleteConfirmation()
        }
    }

    private fun preencherCampos(vaga: VagaResponse) {
        binding.etTitulo.setText(vaga.titulo)
        binding.etDescricao.setText(vaga.descricao)
        binding.etSalario.setText(vaga.salario?.toString())
        binding.etBeneficios.setText(vaga.beneficios)
        selectedEmpresaId = vaga.idEmpresa
        
        loadEmpresas()
    }

    private fun loadEmpresas() {
        binding.loading.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                empresasList = EmpresaClient.api.listarTodas()
                val names = empresasList.map { it.nome ?: "Sem nome" }
                val adapter = ArrayAdapter(this@EditVagaActivity, android.R.layout.simple_dropdown_item_1line, names)
                binding.spinnerEmpresa.setAdapter(adapter)

                // Set initial selection
                val selectedEmpresa = empresasList.find { it.id == selectedEmpresaId }
                if (selectedEmpresa != null) {
                    binding.spinnerEmpresa.setText(selectedEmpresa.nome, false)
                }

                binding.spinnerEmpresa.setOnItemClickListener { _, _, position, _ ->
                    selectedEmpresaId = empresasList[position].id
                }
            } catch (e: Exception) {
                Toast.makeText(this@EditVagaActivity, "Erro ao carregar empresas", Toast.LENGTH_SHORT).show()
            } finally {
                binding.loading.visibility = View.GONE
            }
        }
    }

    private fun validateAndSave() {
        val id = vagaId ?: return
        val titulo = binding.etTitulo.text.toString()
        val descricao = binding.etDescricao.text.toString()
        val salarioStr = binding.etSalario.text.toString()
        val beneficios = binding.etBeneficios.text.toString()
        val empresaId = selectedEmpresaId

        if (empresaId == null || titulo.isEmpty() || descricao.isEmpty()) {
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        val request = UpdateVagaRequest(
            titulo = titulo,
            descricao = descricao,
            salario = if (salarioStr.isNotEmpty()) BigDecimal(salarioStr) else null,
            beneficios = beneficios.ifEmpty { null },
            idEmpresa = empresaId
        )

        updateVaga(id, request)
    }

    private fun updateVaga(id: Long, request: UpdateVagaRequest) {
        binding.loading.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                VagaClient.api.atualizar(id, request)
                Toast.makeText(this@EditVagaActivity, R.string.vaga_atualizada_sucesso, Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@EditVagaActivity, "Erro ao atualizar: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.loading.visibility = View.GONE
            }
        }
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Deletar Vaga")
            .setMessage("Tem certeza que deseja remover esta vaga?")
            .setPositiveButton(R.string.deletar) { _, _ -> deleteVaga() }
            .setNegativeButton(R.string.cancelar, null)
            .show()
    }

    private fun deleteVaga() {
        val id = vagaId ?: return
        binding.loading.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                VagaClient.api.deletar(id)
                Toast.makeText(this@EditVagaActivity, R.string.vaga_removida_sucesso, Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@EditVagaActivity, "Erro ao remover: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.loading.visibility = View.GONE
            }
        }
    }
}
