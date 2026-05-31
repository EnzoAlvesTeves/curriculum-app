package com.example.curriculumapp.ui.profile

import android.os.Bundle
import com.example.curriculumapp.R
import com.example.curriculumapp.databinding.ActivityProfileBinding
import com.example.curriculumapp.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = ProfilePagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_perfil)
                1 -> getString(R.string.tab_senha)
                2 -> getString(R.string.tab_conta)
                else -> null
            }
        }.attach()
    }
}
