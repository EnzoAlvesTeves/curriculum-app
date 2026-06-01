package com.example.curriculumapp.ui.vaga

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curriculumapp.client.vaga.EmpresaClient
import com.example.curriculumapp.client.vaga.VagaClient
import com.example.curriculumapp.databinding.ActivityListVagasBinding
import com.example.curriculumapp.ui.base.BaseActivity
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ListVagasActivity : BaseActivity() {

    private lateinit var binding: ActivityListVagasBinding
    private lateinit var adapter: VagaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListVagasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader(binding.header)
        setupDrawer(binding.drawerLayout, binding.navigationView)

        setupRecyclerView()
        loadData()
    }

    private fun setupRecyclerView() {
        adapter = VagaAdapter(
            emptyList(),
            emptyMap(),
            emptyMap(),
            onBadgeClick = { vaga ->
                val intent = Intent(this, CandidatosVagaActivity::class.java)
                intent.putExtra("VAGA_ID", vaga.id)
                intent.putExtra("VAGA_TITULO", vaga.titulo)
                startActivity(intent)
            }
        ) { vaga ->
            val intent = Intent(this, EditVagaActivity::class.java)
            intent.putExtra("VAGA_DATA", vaga)
            startActivity(intent)
        }
        binding.rvVagas.layoutManager = LinearLayoutManager(this)
        binding.rvVagas.adapter = adapter
    }

    private fun loadData() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val vagasDeferred = async { VagaClient.api.buscarMinhasVagasCriadas() }
                val empresasDeferred = async { EmpresaClient.api.listarTodas() }

                val vagas = vagasDeferred.await()
                val empresas = empresasDeferred.await()

                val empresaMap = empresas.associate { 
                    (it.id ?: 0L) to (it.nome ?: "Empresa sem nome") 
                }

                // Fetch candidate counts for each vacancy in parallel
                val countsMap = vagas.associate { vaga ->
                    val count = try {
                        if (vaga.id != null) {
                            VagaClient.api.listarCandidatos(vaga.id).size
                        } else 0
                    } catch (e: Exception) { 0 }
                    (vaga.id ?: 0L) to count
                }

                adapter.updateData(vagas, empresaMap, countsMap)
            } catch (e: Exception) {
                Toast.makeText(this@ListVagasActivity, "Erro ao carregar dados: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}
