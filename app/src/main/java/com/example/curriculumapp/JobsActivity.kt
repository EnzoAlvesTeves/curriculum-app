package com.example.curriculumapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curriculumapp.databinding.ActivityJobsBinding

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
        
        // Simulating empty list for now
        updateList(emptyList())
    }

    private fun setupRecyclerView() {
        adapter = JobAdapter(emptyList())
        binding.rvJobs.layoutManager = LinearLayoutManager(this)
        binding.rvJobs.adapter = adapter
    }

    private fun updateList(jobs: List<Job>) {
        if (jobs.isEmpty()) {
            binding.rvJobs.visibility = View.GONE
            binding.emptyStateJobs.visibility = View.VISIBLE
        } else {
            binding.rvJobs.visibility = View.VISIBLE
            binding.emptyStateJobs.visibility = View.GONE
            adapter.updateJobs(jobs)
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