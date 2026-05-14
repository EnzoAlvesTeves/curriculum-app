package com.example.curriculumapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curriculumapp.client.candidato.CandidatoClient
import com.example.curriculumapp.client.usuario.dto.UsuarioDTO
import com.example.curriculumapp.client.vaga.CandidatoVagaClient
import com.example.curriculumapp.client.vaga.VagaClient
import com.example.curriculumapp.client.vaga.dto.CandidatoVagaDTO
import com.example.curriculumapp.client.vaga.dto.VagaDTO
import com.example.curriculumapp.databinding.ActivityJobsBinding
import kotlinx.coroutines.launch

class JobsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobsBinding
    private lateinit var adapter: JobAdapter
    private var usuarioDTO: UsuarioDTO? = null
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        usuarioDTO = intent.getSerializableExtra("usuarioDTO") as? UsuarioDTO ?: sessionManager.getSession()

        setupRecyclerView()
        setupBottomNavigation()
        setupClickListeners()

        fetchJobs()
    }

    private fun fetchJobs() {
        lifecycleScope.launch {
            try {
                val vagas = VagaClient.api.listarTodas()
                updateList(vagas)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro ao listar vagas: ${e.message}")
                Toast.makeText(this@JobsActivity, "Erro ao listar vagas", Toast.LENGTH_SHORT).show()
                updateList(emptyList())
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = JobAdapter(
            jobs = emptyList(),
            isAdmin = usuarioDTO?.isAdmin ?: false,
            onApplyClick = { vaga -> handleApply(vaga) }
        )
        binding.rvJobs.layoutManager = LinearLayoutManager(this)
        binding.rvJobs.adapter = adapter
    }

    private fun handleApply(vaga: VagaDTO) {
        val userId = usuarioDTO?.id?.toInt() ?: return
        
        lifecycleScope.launch {
            try {
                // 1. Buscar o perfil do candidato vinculado ao usuário logado
                val candidato = CandidatoClient.api.buscarPorUsuario(userId)
                val candidatoId = candidato.id ?: return@launch

                // 2. Verificar se já existe candidatura para esta vaga
                val candidaturas = CandidatoVagaClient.api.listarCandidaturasPorCandidato(candidatoId)
                val jaInscrito = candidaturas.any { it.vagaId == vaga.id }

                if (jaInscrito) {
                    Toast.makeText(this@JobsActivity, "Você já está inscrito nesta vaga!", Toast.LENGTH_SHORT).show()
                } else {
                    // 3. Realizar a inscrição
                    val novaCandidatura = CandidatoVagaDTO(
                        candidatoId = candidatoId,
                        vagaId = vaga.id ?: 0
                    )
                    CandidatoVagaClient.api.candidatar(novaCandidatura)
                    Toast.makeText(this@JobsActivity, "Candidatura realizada com sucesso!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro ao processar candidatura: ${e.message}")
                Toast.makeText(this@JobsActivity, "Erro ao processar candidatura. Verifique se seu perfil está completo.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateList(vagas: List<VagaDTO>) {
        if (vagas.isEmpty()) {
            binding.rvJobs.visibility = View.GONE
            binding.emptyStateJobs.visibility = View.VISIBLE
        } else {
            binding.rvJobs.visibility = View.VISIBLE
            binding.emptyStateJobs.visibility = View.GONE
            adapter.updateJobs(vagas)
        }
    }

    private fun setupBottomNavigation() {
        binding.navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra("usuarioDTO", usuarioDTO)
            startActivity(intent)
            finish()
        }
        
        binding.navCandidates.setOnClickListener {
            val intent = Intent(this, CandidatesActivity::class.java)
            intent.putExtra("usuarioDTO", usuarioDTO)
            startActivity(intent)
            finish()
        }

        binding.navProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("usuarioDTO", usuarioDTO)
            startActivity(intent)
            finish()
        }
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}