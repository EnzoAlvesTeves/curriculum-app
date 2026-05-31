package com.example.curriculumapp.ui.candidato

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.curriculumapp.ui.candidato.fragments.CandidatoDataFragment
import com.example.curriculumapp.ui.candidato.fragments.EducacaoListFragment
import com.example.curriculumapp.ui.candidato.fragments.EnderecoFragment
import com.example.curriculumapp.ui.candidato.fragments.ExperienciaListFragment
import com.example.curriculumapp.ui.candidato.fragments.HabilidadeListFragment

class CurriculoPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CandidatoDataFragment()
            1 -> EnderecoFragment()
            2 -> EducacaoListFragment()
            3 -> ExperienciaListFragment()
            4 -> HabilidadeListFragment()
            else -> CandidatoDataFragment()
        }
    }
}
