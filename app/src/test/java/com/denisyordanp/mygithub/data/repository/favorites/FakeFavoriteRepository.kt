package com.denisyordanp.mygithub.data.repository.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.denisyordanp.mygithub.models.database.FavoriteUserEntity

class FakeFavoriteRepository : FavoriteRepository {

    private val favorites = MutableLiveData<List<FavoriteUserEntity>>(emptyList())

    override fun getFavoriteByUsername(username: String): LiveData<FavoriteUserEntity?> {
        val currentList = favorites.value
        return liveData {
            var isContains: FavoriteUserEntity? = null
            currentList?.forEach {
                if (it.username == username) {
                    isContains = it
                }
            }
            this.emit(isContains)
        }
    }

    override fun getFavorites(): LiveData<List<FavoriteUserEntity>> = favorites

    override suspend fun insertFavorite(user: FavoriteUserEntity) {
        val currentList = favorites.value
        val newList = currentList?.plus(user)
        favorites.value = newList
    }

    override suspend fun deleteFavorite(user: FavoriteUserEntity) {
        val currentList = favorites.value
        if (!currentList.isNullOrEmpty()) {
            val newList = currentList.minus(user)
            favorites.value = newList
        }
    }

    companion object {
        val fakeFavorite = FavoriteUserEntity("username", "avatarUrl")
    }
}