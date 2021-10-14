package com.denisyordanp.mygithub.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.denisyordanp.mygithub.databinding.UsersViewBinding
import com.denisyordanp.mygithub.models.remote.ResponseSearchUser
import com.denisyordanp.mygithub.utils.load

class UserAdapter(private val users: List<ResponseSearchUser>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var onClickOnUser: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder.from(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position], onClickOnUser)
    }

    override fun getItemCount(): Int = users.size

    class UserViewHolder(private val binding: UsersViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(users: ResponseSearchUser, onClickOnUser: ((String) -> Unit)?) {
            binding.apply {
                usernameTextView.text = users.username
                userImageView.load(users.avatarUrl)
                root.setOnClickListener { onClickOnUser?.invoke(users.username) }
            }
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = UsersViewBinding.inflate(inflater, parent, false)
                return UserViewHolder(binding)
            }
        }
    }
}