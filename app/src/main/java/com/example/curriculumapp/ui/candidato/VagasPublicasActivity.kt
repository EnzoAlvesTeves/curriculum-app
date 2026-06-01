package com.example.curriculumapp.ui.candidato

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curriculumapp.R
import com.example.curriculumapp.client.vaga.EmpresaClient
import com.example.curriculumapp.client.vaga.VagaClient
import com.example.curriculumapp.client.vaga.dto.VagaResponse
import com.example.curriculumapp.databinding.ActivityListVagasBinding
import com.example.curriculumapp.databinding.DialogVagaDetalhesBinding
import com.example.curriculumapp.ui.base.BaseActivity
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class VagasPublicasActivity : BaseActivity() {

    private lateinit var binding: ActivityListVagasBinding
    private lateinit var adapter: VagaPublicaAdapter
    private var empresaMap: Map<Long, String> = emptyMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListVagasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader(binding.header)
        setupDrawer(binding.drawerLayout, binding.navigationView)

        binding.tvTitle.text = "Vagas Disponíveis"

        setupRecyclerView()
        loadData()
    }

    private fun setupRecyclerView() {
        adapter = VagaPublicaAdapter(
            emptyList(),
            emptyMap(),
            onDetailsClick = { vaga -> showVagaDetails(vaga) },
            onApplyClick = { vaga -> candidatarVaga(vaga) }
        )
        binding.rvVagas.layoutManager = LinearLayoutManager(this)
        binding.rvVagas.adapter = adapter
    }

    private fun loadData() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                // Fetch all vacancies and all companies in parallel
                val vagasDeferred = async { VagaClient.api.listar() }
                val empresasDeferred = async { EmpresaClient.api.listarTodas() }

                val vagas = vagasDeferred.await()
                val empresas = empresasDeferred.await()

                empresaMap = empresas.associate { (it.id ?: 0L) to (it.nome ?: "Empresa sem nome") }

                adapter.updateData(vagas, empresaMap)
            } catch (e: Exception) {
                Toast.makeText(this@VagasPublicasActivity, "Erro ao carregar vagas: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun showVagaDetails(vaga: VagaResponse) {
        val dialogBinding = DialogVagaDetalhesBinding.inflate(layoutInflater)
        
        dialogBinding.tvTitulo.text = vaga.titulo
        dialogBinding.tvEmpresa.text = empresaMap[vaga.idEmpresa] ?: "Empresa não identificada"
        dialogBinding.tvDescricao.text = vaga.descricao
        dialogBinding.tvSalario.text = vaga.salario?.toString() ?: "Não informado"
        dialogBinding.tvBeneficios.text = vaga.beneficios ?: "Não informado"

        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setPositiveButton("Candidatar-se") { _, _ -> candidatarVaga(vaga) }
            .setNegativeButton("Fechar", null)
            .show()
    }

    private fun candidatarVaga(vaga: VagaResponse) {
        val idVaga = vaga.id ?: return
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                VagaClient.api.candidatar(idVaga)
                Toast.makeText(this@VagasPublicasActivity, "Candidatura realizada com sucesso!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@VagasPublicasActivity, "Erro ao se candidatar: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}
