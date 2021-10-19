package com.denisyordanp.mygithub.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denisyordanp.mygithub.view.main.MainViewModel

class MainViewModelFactory private constructor(private val preferences: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: MainViewModelFactory? = null

        @JvmStatic
        fun getInstance(preferences: SettingPreferences): MainViewModelFactory {
            if (INSTANCE == null) {
                synchronized(MainViewModelFactory::class.java) {
                    INSTANCE = MainViewModelFactory(preferences)
                }
            }
            return INSTANCE as MainViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}