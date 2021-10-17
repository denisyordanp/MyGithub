package com.denisyordanp.mygithub.view.favorites

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisyordanp.mygithub.data.repository.FavoriteRepository
import com.denisyordanp.mygithub.models.database.FavoriteUserEntity
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : ViewModel() {

    private val repository: FavoriteRepository = FavoriteRepository(application)

    val favorites: LiveData<List<FavoriteUserEntity>> = repository.getFavorites()
    val isFavoritesEmpty: LiveData<Boolean> = Transformations.map(favorites) {
        it.isNullOrEmpty()
    }

    fun removeFavorite(user: FavoriteUserEntity) = viewModelScope.launch {
        repository.deleteFavorite(user)
    }
}