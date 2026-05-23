package com.example.curriculumapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.curriculumapp.client.usuario.dto.UsuarioDTO
import com.example.curriculumapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var usuarioDTO: UsuarioDTO
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        if (sessionManager.getSession() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        } else {
            usuarioDTO = sessionManager.getSession()!!
        }

        setupAdminUI()
        setupClickListeners()
    }

    private fun setupAdminUI() {
        usuarioDTO.let {
            if (it.isAdmin) {
                binding.navCurriculo.visibility = View.GONE
            } else {
                binding.navCandidatos.visibility = View.GONE
            }
        }
    }


//    private fun handleApply(vaga: VagaDTO) {
//        val userId = usuarioDTO?.id?.toInt() ?: return
//
//        lifecycleScope.launch {
//            try {
//                // 1. Buscar o perfil do candidato vinculado ao usuário logado
//                val candidato = CandidatoClient.api.buscarPorUsuario(userId)
//                val candidatoId = candidato.id ?: return@launch
//
//                // 2. Verificar se já existe candidatura para esta vaga
//                val candidaturas = CandidatoVagaClient.api.listarCandidaturasPorCandidato(candidatoId)
//                val jaInscrito = candidaturas.any { it.vagaId == vaga.id }
//
//                if (jaInscrito) {
//                    Toast.makeText(this@HomeActivity, "Você já está inscrito nesta vaga!", Toast.LENGTH_SHORT).show()
//                } else {
//                    // 3. Realizar a inscrição
//                    val novaCandidatura = CandidatoVagaDTO(
//                        candidatoId = candidatoId,
//                        vagaId = vaga.id ?: 0
//                    )
//                    CandidatoVagaClient.api.candidatar(novaCandidatura)
//                    Toast.makeText(this@HomeActivity, "Candidatura realizada com sucesso!", Toast.LENGTH_SHORT).show()
//                }
//            } catch (e: Exception) {
//                Log.e("API_ERROR", "Erro ao processar candidatura: ${e.message}")
//                Toast.makeText(this@HomeActivity, "Erro ao processar candidatura. Verifique se seu perfil está completo.", Toast.LENGTH_LONG).show()
//            }
//        }
//    }

//    private fun fetchRecentJobs() {
//        lifecycleScope.launch {
//            try {
//                val vagas = VagaClient.api.listarTodas()
//                val recentes = if (vagas.size > 3) vagas.take(3) else vagas
//                adapter.updateJobs(recentes)
//            } catch (e: Exception) {
//                Log.e("API_ERROR", "Erro ao carregar vagas: ${e.message}")
//            }
//        }
//    }

    private fun setupClickListeners() {
        // Bottom Navigation
        binding.navCurriculo.setOnClickListener {
            val intent = Intent(this, CandidateRegistrationActivity::class.java)
//            intent.putExtra("usuarioDTO", usuarioDTO)
            startActivity(intent)
        }

        binding.navCandidatos.setOnClickListener {
            val intent = Intent(this, CandidatesActivity::class.java)
//            intent.putExtra("usuarioDTO", usuarioDTO)
            startActivity(intent)
        }

        binding.navVagas.setOnClickListener {
            val intent = Intent(this, JobsActivity::class.java)
//            intent.putExtra("usuarioDTO", usuarioDTO)
            startActivity(intent)
        }

        binding.navProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
//            intent.putExtra("usuarioDTO", usuarioDTO)
            startActivity(intent)
        }
    }
}