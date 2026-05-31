package com.example.curriculumapp.ui.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.curriculumapp.ui.profile.fragments.ChangePasswordFragment
import com.example.curriculumapp.ui.profile.fragments.DeleteAccountFragment
import com.example.curriculumapp.ui.profile.fragments.EditProfileFragment

class ProfilePagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EditProfileFragment()
            1 -> ChangePasswordFragment()
            2 -> DeleteAccountFragment()
            else -> EditProfileFragment()
        }
    }
}
