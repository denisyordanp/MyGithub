package com.denisyordanp.mygithub.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denisyordanp.mygithub.view.detail.DetailViewModel
import com.denisyordanp.mygithub.view.favorites.FavoritesViewModel

class ApplicationViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ApplicationViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ApplicationViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ApplicationViewModelFactory::class.java) {
                    INSTANCE = ApplicationViewModelFactory(application)
                }
            }
            return INSTANCE as ApplicationViewModelFactory
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}