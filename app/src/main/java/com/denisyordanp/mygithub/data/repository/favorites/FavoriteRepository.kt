package com.denisyordanp.mygithub.data.repository.favorites

import androidx.lifecycle.LiveData
import com.denisyordanp.mygithub.models.database.FavoriteUserEntity

interface FavoriteRepository {
    fun getFavoriteByUsername(username: String): LiveData<FavoriteUserEntity?>
    fun getFavorites(): LiveData<List<FavoriteUserEntity>>

    suspend fun insertFavorite(user: FavoriteUserEntity)
    suspend fun deleteFavorite(user: FavoriteUserEntity)
}