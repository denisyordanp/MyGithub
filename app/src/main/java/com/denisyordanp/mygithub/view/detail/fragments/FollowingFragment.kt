package com.denisyordanp.mygithub.view.detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.denisyordanp.mygithub.databinding.FragmentFollowingBinding
import com.denisyordanp.mygithub.models.remote.ResponseFollowUsers
import com.denisyordanp.mygithub.view.adapter.FollowAdapter
import com.denisyordanp.mygithub.view.detail.DetailViewModel

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: FollowAdapter

    private val followingsViewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun setupListener() {
        followingsViewModel.followings.observe(viewLifecycleOwner) {
            showFollowings(it)
        }
        followingsViewModel.isFollowingsLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showFollowings(follows: List<ResponseFollowUsers>) {
        adapter = FollowAdapter(follows)
        binding.followingRecyclerView.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            followingProgressBar.isVisible = isLoading
            followingRecyclerView.isVisible = !isLoading
        }
    }
}