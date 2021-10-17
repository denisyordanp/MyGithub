package com.denisyordanp.mygithub.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.denisyordanp.mygithub.databinding.FavoriteViewBinding
import com.denisyordanp.mygithub.models.database.FavoriteUserEntity
import com.denisyordanp.mygithub.utils.load

class FavoritesAdapter :
    ListAdapter<FavoriteUserEntity, FavoritesAdapter.FavoriteViewHolder>(FAVORITE_COMPARATOR) {

    var onClickUser: ((String) -> Unit)? = null
    var onRemoveFavorite: ((FavoriteUserEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder.from(parent)

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position), onClickUser, onRemoveFavorite)
    }

    class FavoriteViewHolder(private val binding: FavoriteViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            favorite: FavoriteUserEntity,
            onClickUser: ((String) -> Unit)?,
            onRemoveFavorite: ((FavoriteUserEntity) -> Unit)?
        ) {
            binding.apply {
                usernameTextView.text = favorite.username
                userImageView.load(favorite.avatarUrl)
                removeFavoriteImageButton.setOnClickListener { onRemoveFavorite?.invoke(favorite) }
                root.setOnClickListener { onClickUser?.invoke(favorite.username) }
            }
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = FavoriteViewBinding.inflate(inflater, parent, false)
                return FavoriteViewHolder(binding)
            }
        }
    }

    companion object {
        private val FAVORITE_COMPARATOR = object : DiffUtil.ItemCallback<FavoriteUserEntity>() {
            override fun areItemsTheSame(
                oldItem: FavoriteUserEntity,
                newItem: FavoriteUserEntity
            ): Boolean = newItem.username == oldItem.username

            override fun areContentsTheSame(
                oldItem: FavoriteUserEntity,
                newItem: FavoriteUserEntity
            ): Boolean = newItem == oldItem

        }
    }
}