package com.example.curriculumapp.ui.vaga

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curriculumapp.client.vaga.VagaClient
import com.example.curriculumapp.databinding.ActivityListVagasBinding
import com.example.curriculumapp.ui.base.BaseActivity
import kotlinx.coroutines.launch

class CandidatosVagaActivity : BaseActivity() {

    private lateinit var binding: ActivityListVagasBinding
    private lateinit var adapter: CandidatoListAdapter
    private var vagaId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListVagasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader(binding.header)
        setupDrawer(binding.drawerLayout, binding.navigationView)

        vagaId = intent.getLongExtra("VAGA_ID", -1)
        val vagaTitulo = intent.getStringExtra("VAGA_TITULO") ?: "Candidatos"
        
        binding.tvTitle.text = vagaTitulo

        setupRecyclerView()
        loadCandidatos()
    }

    private fun setupRecyclerView() {
        adapter = CandidatoListAdapter(emptyList())
        binding.rvVagas.layoutManager = LinearLayoutManager(this)
        binding.rvVagas.adapter = adapter
    }

    private fun loadCandidatos() {
        if (vagaId == -1L) return

        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val candidatos = VagaClient.api.listarCandidatos(vagaId)
                adapter.updateList(candidatos)
            } catch (e: Exception) {
                Toast.makeText(this@CandidatosVagaActivity, "Erro ao carregar candidatos: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}
