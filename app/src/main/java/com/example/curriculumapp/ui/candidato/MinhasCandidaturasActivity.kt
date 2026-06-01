package com.example.curriculumapp.ui.candidato

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curriculumapp.client.vaga.EmpresaClient
import com.example.curriculumapp.client.vaga.VagaClient
import com.example.curriculumapp.client.vaga.dto.VagaResponse
import com.example.curriculumapp.databinding.ActivityListVagasBinding
import com.example.curriculumapp.ui.base.BaseActivity
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MinhasCandidaturasActivity : BaseActivity() {

    private lateinit var binding: ActivityListVagasBinding
    private lateinit var adapter: CandidaturaAdapter
    private var empresaMap: Map<Long, String> = emptyMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListVagasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader(binding.header)
        setupDrawer(binding.drawerLayout, binding.navigationView)

        binding.tvTitle.text = "Minhas Candidaturas"

        setupRecyclerView()
        loadData()
    }

    private fun setupRecyclerView() {
        adapter = CandidaturaAdapter(
            emptyList(),
            emptyMap(),
            onRemoveClick = { vaga -> showRemoveConfirmation(vaga) }
        )
        binding.rvVagas.layoutManager = LinearLayoutManager(this)
        binding.rvVagas.adapter = adapter
    }

    private fun loadData() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val candidaturasDeferred = async { VagaClient.api.buscarMinhasCandidaturas() }
                val empresasDeferred = async { EmpresaClient.api.listarTodas() }

                val candidaturas = candidaturasDeferred.await()
                val empresas = empresasDeferred.await()

                empresaMap = empresas.associate { (it.id ?: 0L) to (it.nome ?: "Empresa sem nome") }

                adapter.updateData(candidaturas, empresaMap)
            } catch (e: Exception) {
                Toast.makeText(this@MinhasCandidaturasActivity, "Erro ao carregar candidaturas: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun showRemoveConfirmation(vaga: VagaResponse) {
        AlertDialog.Builder(this)
            .setTitle("Remover Candidatura")
            .setMessage("Tem certeza que deseja desistir desta vaga?")
            .setPositiveButton("Sim, remover") { _, _ -> removerCandidatura(vaga) }
            .setNegativeButton("Não", null)
            .show()
    }

    private fun removerCandidatura(vaga: VagaResponse) {
        val idVaga = vaga.id ?: return
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                VagaClient.api.removerCandidatura(idVaga)
                Toast.makeText(this@MinhasCandidaturasActivity, "Candidatura removida.", Toast.LENGTH_SHORT).show()
                loadData() // Reload list
            } catch (e: Exception) {
                Toast.makeText(this@MinhasCandidaturasActivity, "Erro ao remover: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}
