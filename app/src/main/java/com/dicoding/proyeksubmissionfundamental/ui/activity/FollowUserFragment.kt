package com.dicoding.proyeksubmissionfundamental.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.proyeksubmissionfundamental.data.response.ItemsItem
import com.dicoding.proyeksubmissionfundamental.databinding.FragmentDetailUserBinding
import com.dicoding.proyeksubmissionfundamental.ui.adapter.FollowAdapter
import com.dicoding.proyeksubmissionfundamental.ui.viewmodel.FollowViewModel

class FollowUserFragment : Fragment() {

    private lateinit var binding: FragmentDetailUserBinding
    private var position: Int = 0
    private var username: String? = null

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val followViewModel = ViewModelProvider(requireActivity()).get(FollowViewModel::class.java)
        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER)
            username = it.getString(ARG_USERNAME)
        }
        val adapter: FollowAdapter
        if (position == 1){
            username?.let {
                followViewModel.listFollowers(it)
            }
            adapter = FollowAdapter()
        } else {
            username?.let {
                followViewModel.listFollowing(it)
            }
            adapter = FollowAdapter()
        }

        binding.rvFollow.adapter = adapter

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollow.layoutManager = layoutManager

        followViewModel.followers.observe(viewLifecycleOwner) { followers ->
            if (position == 1) {
                followers?.let {
                    setFollowersData(followers)
                }
            }
        }

        followViewModel.following.observe(viewLifecycleOwner) { following ->
            if (position != 1) {
                following?.let {
                    setFollowingData(following)
                }
            }
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followViewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFollowersData(followers: List<ItemsItem>) {
        (binding.rvFollow.adapter as? FollowAdapter)?.submitList(followers)
    }

    private fun setFollowingData(following: List<ItemsItem>) {
        (binding.rvFollow.adapter as? FollowAdapter)?.submitList(following)
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar3.visibility = View.VISIBLE
        } else {
            binding.progressBar3.visibility = View.GONE
        }
    }

}