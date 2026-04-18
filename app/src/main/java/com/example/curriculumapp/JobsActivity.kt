package com.example.curriculumapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curriculumapp.client.vaga.VagaClient
import com.example.curriculumapp.client.vaga.dto.VagaDTO
import com.example.curriculumapp.databinding.ActivityJobsBinding
import kotlinx.coroutines.launch

class JobsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobsBinding
    private lateinit var adapter: JobAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        adapter = JobAdapter(emptyList())
        binding.rvJobs.layoutManager = LinearLayoutManager(this)
        binding.rvJobs.adapter = adapter
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
            startActivity(intent)
            finish()
        }
        
        binding.navCandidates.setOnClickListener {
            val intent = Intent(this, CandidatesActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.navProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
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