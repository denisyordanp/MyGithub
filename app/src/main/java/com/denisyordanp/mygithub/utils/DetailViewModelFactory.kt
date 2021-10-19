package com.denisyordanp.mygithub.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denisyordanp.mygithub.data.database.AppDatabase
import com.denisyordanp.mygithub.data.remote.ApiConfig
import com.denisyordanp.mygithub.data.repository.favorites.FavoriteRepository
import com.denisyordanp.mygithub.data.repository.favorites.FavoriteRepositoryImpl
import com.denisyordanp.mygithub.data.repository.users.UserRepository
import com.denisyordanp.mygithub.data.repository.users.UserRepositoryImpl
import com.denisyordanp.mygithub.view.detail.DetailViewModel

class DetailViewModelFactory private constructor(
    private val favoriteRepository: FavoriteRepository,
    private val userRepository: UserRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: DetailViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): DetailViewModelFactory {
            if (INSTANCE == null) {
                synchronized(DetailViewModelFactory::class.java) {
                    val db = AppDatabase.getDatabase(application)
                    val favoriteRepository = FavoriteRepositoryImpl(db.favoriteDao())
                    val userRepository = UserRepositoryImpl(ApiConfig.getApiService())
                    INSTANCE = DetailViewModelFactory(favoriteRepository, userRepository)
                }
            }
            return INSTANCE as DetailViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(favoriteRepository, userRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }
}