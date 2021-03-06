package com.denisyordanp.mygithub.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.denisyordanp.mygithub.databinding.UsersViewBinding
import com.denisyordanp.mygithub.models.remote.ResponseFollowUsers
import com.denisyordanp.mygithub.utils.load

class FollowAdapter(private val follows: List<ResponseFollowUsers>) :
    RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder =
        FollowViewHolder.from(parent)

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(follows[position])
    }

    override fun getItemCount(): Int = follows.size

    class FollowViewHolder(private val binding: UsersViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(follows: ResponseFollowUsers) {
            binding.apply {
                usernameTextView.text = follows.username
                userImageView.load(follows.avatarUrl)
            }
        }

        companion object {
            fun from(parent: ViewGroup): FollowViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = UsersViewBinding.inflate(inflater, parent, false)
                return FollowViewHolder(binding)
            }
        }
    }
}