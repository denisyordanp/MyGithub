package com.denisyordanp.mygithub.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FavoriteUserEntity.TABLE_NAME)
data class FavoriteUserEntity(
    @PrimaryKey
    val username: String,
    val avatarUrl: String
) {

    companion object {
        const val TABLE_NAME = "table_favorites"
    }
}
