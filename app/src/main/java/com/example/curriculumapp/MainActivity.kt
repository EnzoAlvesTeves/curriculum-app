package com.example.curriculumapp

import android.os.Bundle
import com.example.curriculumapp.databinding.ActivityMainBinding
import com.example.curriculumapp.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup the common header
        setupHeader(binding.header)
        
        // Setup the side menu (Drawer)
        setupDrawer(binding.drawerLayout, binding.navigationView)
    }

    override fun onResume() {
        super.onResume()
        loadHeaderData()
    }
}
