package com.example.curriculumapp.ui.empresa

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.R
import com.example.curriculumapp.client.vaga.EmpresaClient
import com.example.curriculumapp.client.vaga.dto.EmpresaResponse
import com.example.curriculumapp.client.vaga.dto.UpdateEmpresaRequest
import com.example.curriculumapp.databinding.ActivityEditEmpresaBinding
import com.example.curriculumapp.ui.base.BaseActivity
import kotlinx.coroutines.launch

class EditEmpresaActivity : BaseActivity() {

    private lateinit var binding: ActivityEditEmpresaBinding
    private var empresaId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditEmpresaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader(binding.header)
        setupDrawer(binding.drawerLayout, binding.navigationView)

        val empresa = intent.getSerializableExtra("EMPRESA_DATA") as? EmpresaResponse
        if (empresa != null) {
            empresaId = empresa.id
            preencherCampos(empresa)
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

    private fun preencherCampos(empresa: EmpresaResponse) {
        binding.etNome.setText(empresa.nome)
        binding.etEstado.setText(empresa.estado)
        binding.etCidade.setText(empresa.cidade)
        binding.etBairro.setText(empresa.bairro)
    }

    private fun validateAndSave() {
        val id = empresaId ?: return
        val nome = binding.etNome.text.toString()
        val estado = binding.etEstado.text.toString()
        val cidade = binding.etCidade.text.toString()
        val bairro = binding.etBairro.text.toString()

        if (nome.isEmpty() || estado.isEmpty() || cidade.isEmpty()) {
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        val request = UpdateEmpresaRequest(
            nome = nome,
            estado = estado,
            cidade = cidade,
            bairro = bairro.ifEmpty { null }
        )

        updateEmpresa(id, request)
    }

    private fun updateEmpresa(id: Long, request: UpdateEmpresaRequest) {
        binding.loading.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                EmpresaClient.api.atualizar(id, request)
                Toast.makeText(this@EditEmpresaActivity, R.string.empresa_atualizada_sucesso, Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@EditEmpresaActivity, "Erro ao atualizar: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.loading.visibility = View.GONE
            }
        }
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle(R.string.deletar_empresa)
            .setMessage(R.string.confirmar_exclusao_empresa)
            .setPositiveButton(R.string.deletar) { _, _ -> deleteEmpresa() }
            .setNegativeButton(R.string.cancelar, null)
            .show()
    }

    private fun deleteEmpresa() {
        val id = empresaId ?: return
        binding.loading.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                EmpresaClient.api.deletar(id)
                Toast.makeText(this@EditEmpresaActivity, R.string.empresa_removida_sucesso, Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@EditEmpresaActivity, "Erro ao remover: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.loading.visibility = View.GONE
            }
        }
    }
}
