package com.denisyordanp.mygithub.data.repository.favorites

import androidx.lifecycle.LiveData
import com.denisyordanp.mygithub.data.database.FavoriteDao
import com.denisyordanp.mygithub.models.database.FavoriteUserEntity

class FavoriteRepositoryImpl(
    private val dao: FavoriteDao
) : FavoriteRepository {

    override fun getFavoriteByUsername(username: String): LiveData<FavoriteUserEntity?> =
        dao.getFavoriteByUsername(username)

    override fun getFavorites(): LiveData<List<FavoriteUserEntity>> = dao.getFavorites()

    override suspend fun insertFavorite(user: FavoriteUserEntity) {
        dao.insertFavorite(user)
    }

    override suspend fun deleteFavorite(user: FavoriteUserEntity) {
        dao.deleteFavorite(user)
    }
}