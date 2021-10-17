package com.denisyordanp.mygithub.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.denisyordanp.mygithub.models.database.FavoriteUserEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(user: FavoriteUserEntity)

    @Query("SELECT * FROM ${FavoriteUserEntity.TABLE_NAME}")
    fun getFavorites(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT * FROM ${FavoriteUserEntity.TABLE_NAME} WHERE username = :username")
    fun getFavoriteByUsername(username: String): LiveData<FavoriteUserEntity>

    @Delete
    suspend fun deleteFavorite(user: FavoriteUserEntity)
}