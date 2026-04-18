package com.example.curriculumapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.curriculumapp.client.usuario.dto.UsuarioDTO
import com.example.curriculumapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var usuarioDTO: UsuarioDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuarioDTO = intent.getSerializableExtra("usuarioDTO") as? UsuarioDTO

        setupClickListeners()
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
}