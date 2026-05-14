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
import com.example.curriculumapp.databinding.ActivityHomeBinding
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: JobAdapter
    private var usuarioDTO: UsuarioDTO? = null
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        usuarioDTO = intent.getSerializableExtra("usuarioDTO") as? UsuarioDTO ?: sessionManager.getSession()

        if (usuarioDTO == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setupAdminUI()
        setupRecyclerView()
        setupClickListeners()
        fetchRecentJobs()
    }

    private fun setupAdminUI() {
        val isAdmin = usuarioDTO?.isAdmin ?: false
        if (isAdmin) {
            binding.cardRegisterJob.visibility = View.VISIBLE
            binding.navJobs.visibility = View.VISIBLE
            binding.cardRegisterCandidate.visibility = View.GONE
        } else {
            binding.cardRegisterJob.visibility = View.GONE
            binding.navJobs.visibility = View.GONE
            binding.cardRegisterCandidate.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        adapter = JobAdapter(
            jobs = emptyList(),
            isAdmin = usuarioDTO?.isAdmin ?: false,
            onApplyClick = { vaga -> handleApply(vaga) }
        )
        binding.rvRecentJobs.layoutManager = LinearLayoutManager(this)
        binding.rvRecentJobs.adapter = adapter
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
                    Toast.makeText(this@HomeActivity, "Você já está inscrito nesta vaga!", Toast.LENGTH_SHORT).show()
                } else {
                    // 3. Realizar a inscrição
                    val novaCandidatura = CandidatoVagaDTO(
                        candidatoId = candidatoId,
                        vagaId = vaga.id ?: 0
                    )
                    CandidatoVagaClient.api.candidatar(novaCandidatura)
                    Toast.makeText(this@HomeActivity, "Candidatura realizada com sucesso!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro ao processar candidatura: ${e.message}")
                Toast.makeText(this@HomeActivity, "Erro ao processar candidatura. Verifique se seu perfil está completo.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun fetchRecentJobs() {
        lifecycleScope.launch {
            try {
                val vagas = VagaClient.api.listarTodas()
                val recentes = if (vagas.size > 3) vagas.take(3) else vagas
                adapter.updateJobs(recentes)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro ao carregar vagas: ${e.message}")
            }
        }
    }

    private fun setupClickListeners() {
        binding.cardRegisterCandidate.setOnClickListener {
            val intent = Intent(this, CandidateRegistrationActivity::class.java)
            intent.putExtra("usuarioDTO", usuarioDTO)
            startActivity(intent)
        }

        binding.cardSearchCandidates.setOnClickListener {
            val intent = Intent(this, CandidatesActivity::class.java)
            intent.putExtra("usuarioDTO", usuarioDTO)
            startActivity(intent)
        }

        binding.cardRegisterJob.setOnClickListener {
            val intent = Intent(this, JobRegistrationActivity::class.java)
            intent.putExtra("usuarioDTO", usuarioDTO)
            startActivity(intent)
        }

        // Bottom Navigation
        binding.navCandidates.setOnClickListener {
            val intent = Intent(this, CandidatesActivity::class.java)
            intent.putExtra("usuarioDTO", usuarioDTO)
            startActivity(intent)
        }

        binding.navJobs.setOnClickListener {
            val intent = Intent(this, JobsActivity::class.java)
            intent.putExtra("usuarioDTO", usuarioDTO)
            startActivity(intent)
        }

        binding.navProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("usuarioDTO", usuarioDTO)
            startActivity(intent)
        }
    }
    
    override fun onResume() {
        super.onResume()
        fetchRecentJobs() // Atualiza a lista ao voltar para a tela
    }
}