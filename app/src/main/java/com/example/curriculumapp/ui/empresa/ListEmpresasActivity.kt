package com.example.curriculumapp.ui.empresa

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curriculumapp.client.vaga.EmpresaClient
import com.example.curriculumapp.databinding.ActivityListEmpresasBinding
import com.example.curriculumapp.ui.base.BaseActivity
import kotlinx.coroutines.launch

class ListEmpresasActivity : BaseActivity() {

    private lateinit var binding: ActivityListEmpresasBinding
    private lateinit var adapter: EmpresaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListEmpresasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader(binding.header)
        setupDrawer(binding.drawerLayout, binding.navigationView)

        setupRecyclerView()
        loadEmpresas()
    }

    private fun setupRecyclerView() {
        adapter = EmpresaAdapter(emptyList()) { empresa ->
            val intent = Intent(this, EditEmpresaActivity::class.java)
            intent.putExtra("EMPRESA_DATA", empresa)
            startActivity(intent)
        }
        binding.rvEmpresas.layoutManager = LinearLayoutManager(this)
        binding.rvEmpresas.adapter = adapter
    }

    private fun loadEmpresas() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val empresas = EmpresaClient.api.listarTodas()
                adapter.updateList(empresas)
            } catch (e: Exception) {
                Toast.makeText(this@ListEmpresasActivity, "Erro ao carregar empresas: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadEmpresas()
    }
}
