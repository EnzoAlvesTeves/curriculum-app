package com.example.curriculumapp.ui.empresa

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curriculumapp.client.vaga.EmpresaClient
import com.example.curriculumapp.client.vaga.VagaClient
import com.example.curriculumapp.databinding.ActivityListEmpresasBinding
import com.example.curriculumapp.ui.base.BaseActivity
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ListEmpresasActivity : BaseActivity() {

    private lateinit var binding: ActivityListEmpresasBinding
    private lateinit var adapter: EmpresaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListEmpresasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader(binding.header)
        setupDrawer(binding.drawerLayout, binding.navigationView)

        setupRecyclerView()
        loadData()
    }

    private fun setupRecyclerView() {
        adapter = EmpresaAdapter(emptyList()) { empresa ->
            val intent = Intent(this, EditEmpresaActivity::class.java)
            intent.putExtra("EMPRESA_DATA", empresa)
            startActivity(intent)
        }
        binding.rvEmpresas.layoutManager = LinearLayoutManager(this)
        binding.rvEmpresas.adapter = adapter
    }

    private fun loadData() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                // Fetch companies and vacancies in parallel
                val empresasDeferred = async { EmpresaClient.api.listarTodas() }
                val vagasDeferred = async { VagaClient.api.buscarMinhasVagasCriadas() }

                val empresas = empresasDeferred.await()
                val vagas = vagasDeferred.await()

                // Calculate vacancy counts per company
                val countsMap = vagas.filter { it.idEmpresa != null }
                    .groupBy { it.idEmpresa!! }
                    .mapValues { it.value.size }

                adapter.updateData(empresas, countsMap)
            } catch (e: Exception) {
                Toast.makeText(this@ListEmpresasActivity, "Erro ao carregar dados: ${e.message}", Toast.LENGTH_SHORT).show()
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
