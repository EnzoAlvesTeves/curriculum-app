package com.example.curriculumapp.ui.candidato

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.curriculumapp.R
import com.example.curriculumapp.databinding.ActivityCurriculoBinding
import com.example.curriculumapp.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator

class CurriculoActivity : BaseActivity() {

    private lateinit var binding: ActivityCurriculoBinding
    private val viewModel: CurriculoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurriculoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader(binding.header)
        setupDrawer(binding.drawerLayout, binding.navigationView)

        binding.btnBack.setOnClickListener { finish() }

        setupViewPager()
        observeViewModel()

        viewModel.loadCandidato()
    }

    private fun setupViewPager() {
        val adapter = CurriculoPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Dados"
                1 -> "Endereço"
                2 -> "Educação"
                3 -> "Experiência"
                4 -> "Habilidades"
                else -> null
            }
        }.attach()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { loading ->
            binding.loading.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }
}
