package com.denisyordanp.mygithub.view.detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.denisyordanp.mygithub.databinding.FragmentRepositoriesBinding
import com.denisyordanp.mygithub.models.remote.ResponseRepository
import com.denisyordanp.mygithub.view.adapter.RepositoryAdapter
import com.denisyordanp.mygithub.view.detail.DetailViewModel

class RepositoriesFragment : Fragment() {

    private var binding: FragmentRepositoriesBinding? = null
    private lateinit var adapter: RepositoryAdapter

    private val repositoriesViewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
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
        repositoriesViewModel.repositories.observe(viewLifecycleOwner) {
            showRepositories(it)
        }
        repositoriesViewModel.isRepositoriesLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showRepositories(repositories: List<ResponseRepository>) {
        adapter = RepositoryAdapter(repositories)
        binding?.repositoriesRecyclerView?.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.apply {
            repositoriesProgressBar.isVisible = isLoading
            repositoriesRecyclerView.isVisible = !isLoading
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}