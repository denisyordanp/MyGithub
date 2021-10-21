package com.denisyordanp.mygithub.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denisyordanp.mygithub.view.search.SearchViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SettingPreferences.SETTING_PREF)

class SearchViewModelFactory private constructor(private val preferences: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: SearchViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): SearchViewModelFactory {
            if (INSTANCE == null) {
                synchronized(SearchViewModelFactory::class.java) {
                    val preference = SettingPreferences.getInstance(context.dataStore)
                    INSTANCE = SearchViewModelFactory(preference)
                }
            }
            return INSTANCE as SearchViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}