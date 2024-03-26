package com.dicoding.proyeksubmissionfundamental.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.proyeksubmissionfundamental.ui.activity.FollowUserFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowUserFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowUserFragment.ARG_SECTION_NUMBER, position + 1)
            putString(FollowUserFragment.ARG_USERNAME, username)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}