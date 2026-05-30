package com.example.curriculumapp.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.CurriculumApplication
import com.example.curriculumapp.MainActivity
import com.example.curriculumapp.R
import com.example.curriculumapp.client.usuario.UsuarioClient
import com.example.curriculumapp.databinding.IncludeHeaderBinding
import com.example.curriculumapp.model.TipoUsuario
import com.example.curriculumapp.ui.candidato.CurriculoActivity
import com.example.curriculumapp.ui.candidato.MinhasCandidaturasActivity
import com.example.curriculumapp.ui.candidato.VagasPublicasActivity
import com.example.curriculumapp.ui.empresa.CreateEmpresaActivity
import com.example.curriculumapp.ui.empresa.ListEmpresasActivity
import com.example.curriculumapp.ui.login.LoginActivity
import com.example.curriculumapp.ui.profile.ProfileActivity
import com.example.curriculumapp.ui.vaga.CreateVagaActivity
import com.example.curriculumapp.ui.vaga.ListVagasActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

abstract class BaseActivity : AppCompatActivity() {

    protected var headerBinding: IncludeHeaderBinding? = null
    protected var drawerLayout: DrawerLayout? = null
    protected var navigationView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setupHeader(binding: IncludeHeaderBinding) {
        headerBinding = binding

        headerBinding?.btnHeaderMenu?.setOnClickListener {
            drawerLayout?.openDrawer(GravityCompat.START)
        }

        loadHeaderData()
    }

    protected fun setupDrawer(drawer: DrawerLayout, navView: NavigationView) {
        this.drawerLayout = drawer
        this.navigationView = navView

        navigationView?.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    if (this !is MainActivity) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                }
                R.id.nav_profile -> {
                    if (this !is ProfileActivity) {
                        startActivity(Intent(this, ProfileActivity::class.java))
                    }
                }
                R.id.nav_logout -> logout()
                // RH Actions
                R.id.nav_cadastrar_empresa -> startActivity(Intent(this, CreateEmpresaActivity::class.java))
                R.id.nav_minhas_empresas -> startActivity(Intent(this, ListEmpresasActivity::class.java))
                R.id.nav_cadastrar_vaga -> startActivity(Intent(this, CreateVagaActivity::class.java))
                R.id.nav_minhas_vagas -> startActivity(Intent(this, ListVagasActivity::class.java))
                // Candidato Actions
                R.id.nav_vagas_candidato -> startActivity(Intent(this, VagasPublicasActivity::class.java))
                R.id.nav_curriculo -> startActivity(Intent(this, CurriculoActivity::class.java))
                R.id.nav_minhas_candidaturas -> startActivity(Intent(this, MinhasCandidaturasActivity::class.java))
            }
            drawerLayout?.closeDrawer(GravityCompat.START)
            true
        }
    }

    protected fun loadHeaderData() {
        lifecycleScope.launch {
            try {
                val user = UsuarioClient.api.getMe()
                headerBinding?.let {
                    it.tvHeaderName.text = user.nome
                    it.tvHeaderEmail.text = user.email
                }
                
                // Show groups based on user type
                navigationView?.menu?.setGroupVisible(R.id.group_rh, user.tipo == TipoUsuario.RH.name)
                navigationView?.menu?.setGroupVisible(R.id.group_candidato, user.tipo == TipoUsuario.CANDIDATO.name)

            } catch (e: Exception) {
                // If it fails, maybe token expired and refresh failed
            }
        }
    }

    protected fun logout() {
        CurriculumApplication.instance.tokenManager.clearTokens()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
