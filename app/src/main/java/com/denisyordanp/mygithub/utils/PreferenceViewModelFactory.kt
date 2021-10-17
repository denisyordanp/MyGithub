package com.denisyordanp.mygithub.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denisyordanp.mygithub.view.main.MainViewModel

class PreferenceViewModelFactory private constructor(private val preferences: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: PreferenceViewModelFactory? = null

        @JvmStatic
        fun getInstance(preferences: SettingPreferences): PreferenceViewModelFactory {
            if (INSTANCE == null) {
                synchronized(PreferenceViewModelFactory::class.java) {
                    INSTANCE = PreferenceViewModelFactory(preferences)
                }
            }
            return INSTANCE as PreferenceViewModelFactory
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}