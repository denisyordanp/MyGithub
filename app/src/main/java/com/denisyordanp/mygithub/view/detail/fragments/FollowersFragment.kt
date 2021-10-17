package com.denisyordanp.mygithub.view.detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.denisyordanp.mygithub.databinding.FragmentFollowersBinding
import com.denisyordanp.mygithub.models.remote.ResponseFollowUsers
import com.denisyordanp.mygithub.view.adapter.FollowAdapter
import com.denisyordanp.mygithub.view.detail.DetailViewModel

class FollowersFragment : Fragment() {

    private var binding: FragmentFollowersBinding? = null
    private lateinit var adapter: FollowAdapter

    private val followersViewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    override fun onResume() {
        super.onResume()
        binding?.root?.requestLayout()
    }

    private fun setupListener() {
        followersViewModel.followers.observe(viewLifecycleOwner) {
            it?.let { showFollowers(it) }
        }
        followersViewModel.isFollowersLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showFollowers(follows: List<ResponseFollowUsers>) {
        adapter = FollowAdapter(follows)
        binding?.followersRecyclerView?.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.apply {
            followersProgressBar.isVisible = isLoading
            followersRecyclerView.isVisible = !isLoading
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}