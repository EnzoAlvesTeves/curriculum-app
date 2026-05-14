package com.example.curriculumapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.curriculumapp.client.usuario.dto.UsuarioDTO
import com.example.curriculumapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private var usuarioDTO: UsuarioDTO? = null
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        // Busca do Intent ou da Sessão persistente (Garante que o Admin apareça)
        usuarioDTO = intent.getSerializableExtra("usuarioDTO") as? UsuarioDTO ?: sessionManager.getSession()

        if (usuarioDTO != null) {
            binding.tvUserName.text = usuarioDTO!!.nome
            binding.tvUserEmail.text = usuarioDTO!!.email
        }

        setupAdminUI()
        setupClickListeners()
        setupBottomNavigation()
    }

    private fun setupAdminUI() {
        val isAdmin = usuarioDTO?.isAdmin ?: false
        if (isAdmin) {
            binding.navJobs.visibility = View.VISIBLE
        } else {
            binding.navJobs.visibility = View.GONE
        }
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.cardLogout.setOnClickListener {
            sessionManager.logout() // Limpa o "localStorage"
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
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

        binding.navJobs.setOnClickListener {
            val intent = Intent(this, JobsActivity::class.java)
            intent.putExtra("usuarioDTO", usuarioDTO)
            startActivity(intent)
            finish()
        }
    }
}