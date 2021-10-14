package com.denisyordanp.mygithub.view.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.denisyordanp.mygithub.R
import com.denisyordanp.mygithub.databinding.FragmentSearchUserBinding
import com.denisyordanp.mygithub.models.remote.ResponseSearchUser
import com.denisyordanp.mygithub.utils.EventObserver
import com.denisyordanp.mygithub.view.adapter.UserAdapter
import com.google.android.material.snackbar.Snackbar

class SearchUserFragment : Fragment() {

    private var binding: FragmentSearchUserBinding? = null

    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (binding == null) {
            val binding = FragmentSearchUserBinding.inflate(layoutInflater, container, false)
            this.binding = binding
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListener()
    }

    private fun setupRecyclerView() {
        binding?.usersRecyclerView?.setHasFixedSize(true)
    }

    private fun setupListener() {
        searchViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        searchViewModel.searchUsers.observe(viewLifecycleOwner) {
            showUsers(it)
        }
        searchViewModel.showSnackEvent.observe(viewLifecycleOwner, EventObserver {
            showError(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.let {
            inflater.inflate(R.menu.search_menu, it)
            setupSearchView(it)
        }
    }

    private fun setupSearchView(menu: Menu) {
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.search_username)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun searchUser(username: String) {
        searchViewModel.searchUser(username)
    }

    private fun showError(message: Int) {
        showLoading(false)
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.apply {
            searchProgressBar.isVisible = isLoading
            usersRecyclerView.isVisible = !isLoading
        }
    }

    private fun showUsers(users: List<ResponseSearchUser>) {
        showLoading(false)
        val adapter = UserAdapter(users)
        adapter.onClickOnUser = { toDetail(it) }
        binding?.usersRecyclerView?.adapter = adapter
    }

    private fun toDetail(username: String) {
        val action =
            SearchUserFragmentDirections.actionSearchUserFragmentToDetailUserFragment(username)
        findNavController().navigate(action)
    }
}