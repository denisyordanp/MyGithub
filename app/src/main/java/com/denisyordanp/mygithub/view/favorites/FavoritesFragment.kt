package com.denisyordanp.mygithub.view.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denisyordanp.mygithub.databinding.FragmentFavoritesBinding
import com.denisyordanp.mygithub.utils.ApplicationViewModelFactory
import com.denisyordanp.mygithub.view.adapter.FavoritesAdapter

class FavoritesFragment : Fragment() {

    private var binding: FragmentFavoritesBinding? = null
    private lateinit var adapter: FavoritesAdapter

    private var favoritesViewModel: FavoritesViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupAdapter()
        setupListener()
    }

    private fun setupViewModel() {
        activity?.application?.let {
            val factory = ApplicationViewModelFactory.getInstance(it)
            favoritesViewModel =
                ViewModelProvider(this, factory).get(FavoritesViewModel::class.java)
        }
    }

    private fun setupAdapter() {
        adapter = FavoritesAdapter().apply {
            onClickUser = { toDetail(it) }
            onRemoveFavorite = { favoritesViewModel?.removeFavorite(it) }
        }
        binding?.favoritesRecyclerView?.adapter = adapter
    }

    private fun setupListener() {
        favoritesViewModel?.favorites?.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        favoritesViewModel?.isFavoritesEmpty?.observe(viewLifecycleOwner) {
            binding?.noFavoriteTextView?.isVisible = it
        }
    }

    private fun toDetail(username: String) {
        val action =
            FavoritesFragmentDirections.actionFavoritesFragmentToDetailUserFragment(username)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}