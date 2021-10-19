package com.denisyordanp.mygithub.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denisyordanp.mygithub.data.database.AppDatabase
import com.denisyordanp.mygithub.data.repository.favorites.FavoriteRepositoryImpl
import com.denisyordanp.mygithub.view.favorites.FavoritesViewModel

class FavoriteViewModelFactory private constructor(private val repository: FavoriteRepositoryImpl) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavoriteViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoriteViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteViewModelFactory::class.java) {
                    val db = AppDatabase.getDatabase(application)
                    val repository = FavoriteRepositoryImpl(db.favoriteDao())
                    INSTANCE = FavoriteViewModelFactory(repository)
                }
            }
            return INSTANCE as FavoriteViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                FavoritesViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }
}