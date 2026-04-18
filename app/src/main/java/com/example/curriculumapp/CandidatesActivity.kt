package com.example.curriculumapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curriculumapp.databinding.ActivityCandidatesBinding

class CandidatesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCandidatesBinding
    private lateinit var adapter: CandidateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCandidatesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupBottomNavigation()
        
        // Simulating data check (if empty, show empty state)
        updateList(emptyList()) 
    }

    private fun setupRecyclerView() {
        adapter = CandidateAdapter(emptyList())
        binding.rvCandidates.layoutManager = LinearLayoutManager(this)
        binding.rvCandidates.adapter = adapter
    }

    private fun updateList(candidates: List<Candidate>) {
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
            startActivity(intent)
            finish()
        }

        binding.navJobs.setOnClickListener {
            val intent = Intent(this, JobsActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.navProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}