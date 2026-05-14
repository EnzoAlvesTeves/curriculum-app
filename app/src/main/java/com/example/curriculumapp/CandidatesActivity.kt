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
import com.example.curriculumapp.client.candidato.dto.CandidatoDTO
import com.example.curriculumapp.client.usuario.dto.UsuarioDTO
import com.example.curriculumapp.databinding.ActivityCandidatesBinding
import kotlinx.coroutines.launch

class CandidatesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCandidatesBinding
    private lateinit var adapter: CandidateAdapter
    private var usuarioDTO: UsuarioDTO? = null
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCandidatesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        usuarioDTO = intent.getSerializableExtra("usuarioDTO") as? UsuarioDTO ?: sessionManager.getSession()

        setupRecyclerView()
        setupBottomNavigation()
        fetchCandidates()
    }

    private fun setupRecyclerView() {
        adapter = CandidateAdapter(emptyList())
        binding.rvCandidates.layoutManager = LinearLayoutManager(this)
        binding.rvCandidates.adapter = adapter
    }

    private fun fetchCandidates() {
        lifecycleScope.launch {
            try {
                val candidates = CandidatoClient.api.listarTodos()
                updateList(candidates)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro ao listar candidatos: ${e.message}")
                Toast.makeText(this@CandidatesActivity, "Erro ao carregar candidatos", Toast.LENGTH_SHORT).show()
                updateList(emptyList())
            }
        }
    }

    private fun updateList(candidates: List<CandidatoDTO>) {
        if (candidates.isEmpty()) {
            binding.rvCandidates.visibility = View.GONE
            binding.emptyState.visibility = View.VISIBLE
        } else {
            binding.rvCandidates.visibility = View.VISIBLE
            binding.emptyState.visibility = View.GONE
            adapter.updateCandidates(candidates)
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

        binding.navJobs.setOnClickListener {
            val intent = Intent(this, JobsActivity::class.java)
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

    override fun onResume() {
        super.onResume()
        fetchCandidates()
    }
}