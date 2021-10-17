package com.denisyordanp.mygithub.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.denisyordanp.mygithub.data.database.AppDatabase
import com.denisyordanp.mygithub.data.database.FavoriteDao
import com.denisyordanp.mygithub.models.database.FavoriteUserEntity

class FavoriteRepository(application: Application) {

    private val favoriteDao: FavoriteDao

    init {
        val db = AppDatabase.getDatabase(application)
        favoriteDao = db.favoriteDao()
    }

    fun getFavoriteByUsername(username: String): LiveData<FavoriteUserEntity> =
        favoriteDao.getFavoriteByUsername(username)

    fun getFavorites(): LiveData<List<FavoriteUserEntity>> = favoriteDao.getFavorites()

    suspend fun insertFavorite(user: FavoriteUserEntity) {
        favoriteDao.insertFavorite(user)
    }

    suspend fun deleteFavorite(user: FavoriteUserEntity) {
        favoriteDao.deleteFavorite(user)
    }
}