package com.example.curriculumapp.ui.empresa

import android.os.Bundle
import com.example.curriculumapp.databinding.ActivityPlaceholderBinding
import com.example.curriculumapp.ui.base.BaseActivity

class ListEmpresasActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPlaceholderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupHeader(binding.header)
        setupDrawer(binding.drawerLayout, binding.navigationView)
        
        binding.tvTitle.text = "Minhas Empresas"
    }
}
