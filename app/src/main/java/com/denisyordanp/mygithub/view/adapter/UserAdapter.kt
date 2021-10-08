package com.denisyordanp.mygithub.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denisyordanp.mygithub.databinding.UsersViewBinding
import com.denisyordanp.mygithub.models.User

class UserAdapter(private val users: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var onClickOnUser: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder.from(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position], onClickOnUser)
    }

    override fun getItemCount(): Int = users.size

    class UserViewHolder(private val binding: UsersViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = UsersViewBinding.inflate(inflater, parent, false)
                return UserViewHolder(binding)
            }
        }

        fun bind(user: User, onClickOnUser: ((User) -> Unit)?) {
            binding.apply {
                nameTextView.text = user.name
                usernameTextView.text = user.username
                Glide.with(root.context)
                    .load(user.avatar)
                    .into(userImageView)
                root.setOnClickListener { onClickOnUser?.invoke(user) }
            }
        }
    }
}