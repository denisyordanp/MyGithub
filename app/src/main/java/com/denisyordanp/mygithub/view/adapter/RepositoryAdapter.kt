package com.denisyordanp.mygithub.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.denisyordanp.mygithub.databinding.RepositoryViewBinding
import com.denisyordanp.mygithub.models.remote.ResponseRepository

class RepositoryAdapter(private val repositories: List<ResponseRepository>) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
        RepositoryViewHolder.from(parent)

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount(): Int = repositories.size

    class RepositoryViewHolder(private val binding: RepositoryViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repository: ResponseRepository) {
            binding.apply {
                repoNameTextView.text = repository.name
                descriptionTextView.text = repository.description
            }
        }

        companion object {
            fun from(parent: ViewGroup): RepositoryViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RepositoryViewBinding.inflate(inflater, parent, false)
                return RepositoryViewHolder(binding)
            }
        }
    }
}