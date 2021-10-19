package com.denisyordanp.mygithub.view.detail

import androidx.lifecycle.*
import com.denisyordanp.mygithub.R
import com.denisyordanp.mygithub.data.repository.favorites.FavoriteRepository
import com.denisyordanp.mygithub.data.repository.users.UserRepository
import com.denisyordanp.mygithub.models.database.FavoriteUserEntity
import com.denisyordanp.mygithub.models.remote.ResponseDetailUser
import com.denisyordanp.mygithub.models.remote.ResponseFollowUsers
import com.denisyordanp.mygithub.models.remote.ResponseRepository
import com.denisyordanp.mygithub.utils.Event
import kotlinx.coroutines.launch

class DetailViewModel(
    private val favoriteRepository: FavoriteRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    // DATA
    private val _detailUser = MutableLiveData<ResponseDetailUser?>()
    val detailUser: LiveData<ResponseDetailUser?> = _detailUser

    private val _followers = MutableLiveData<List<ResponseFollowUsers>?>()
    val followers: LiveData<List<ResponseFollowUsers>?> = _followers

    private val _followings = MutableLiveData<List<ResponseFollowUsers>?>()
    val followings: LiveData<List<ResponseFollowUsers>?> = _followings

    private val _repositories = MutableLiveData<List<ResponseRepository>?>()
    val repositories: LiveData<List<ResponseRepository>?> = _repositories

    // LOADING
    private val _isDetailUserLoading = MutableLiveData<Boolean>()
    val isDetailUserLoading: LiveData<Boolean> = _isDetailUserLoading

    private val _isFollowersLoading = MutableLiveData<Boolean>()
    val isFollowersLoading: LiveData<Boolean> = _isFollowersLoading

    private val _isFollowingsLoading = MutableLiveData<Boolean>()
    val isFollowingsLoading: LiveData<Boolean> = _isFollowingsLoading

    private val _isRepositoriesLoading = MutableLiveData<Boolean>()
    val isRepositoriesLoading: LiveData<Boolean> = _isRepositoriesLoading

    // SNACK BAR
    private val _showSnackEvent = MutableLiveData<Event<Int>>()
    val showSnackEvent: LiveData<Event<Int>> = _showSnackEvent

    fun isFavorite(username: String): LiveData<Boolean> =
        Transformations.map(favoriteRepository.getFavoriteByUsername(username)) {
            it != null
        }

    fun requestUserData(username: String) = viewModelScope.launch {
        username.apply {
            requestDetailUser()
            requestFollowers()
            requestFollowings()
            requestRepositories()
        }
    }

    private suspend fun String.requestDetailUser() {
        _isDetailUserLoading.value = true
        try {
            _detailUser.value = userRepository.requestUserDetail(this)
        } catch (e: Exception) {
            e.printStackTrace()
            _showSnackEvent.value = Event(R.string.request_error)
        }
        _isDetailUserLoading.value = false
    }

    private suspend fun String.requestFollowers() {
        _isFollowersLoading.value = true
        try {
            _followers.value = userRepository.requestFollowers(this)
        } catch (e: Exception) {
            e.printStackTrace()
            _showSnackEvent.value = Event(R.string.request_error)
        }
        _isFollowersLoading.value = false
    }

    private suspend fun String.requestFollowings() {
        _isFollowingsLoading.value = true
        try {
            _followings.value = userRepository.requestFollowings(this)
        } catch (e: Exception) {
            e.printStackTrace()
            _showSnackEvent.value = Event(R.string.request_error)
        }
        _isFollowingsLoading.value = false
    }

    private suspend fun String.requestRepositories() {
        _isRepositoriesLoading.value = true
        try {
            _repositories.value = userRepository.requestRepositories(this)
        } catch (e: Exception) {
            e.printStackTrace()
            _showSnackEvent.value = Event(R.string.request_error)
        }
        _isRepositoriesLoading.value = false
    }

    fun setFavoriteUser(user: ResponseDetailUser, isFavorite: Boolean) = viewModelScope.launch {
        if (isFavorite) {
            favoriteRepository.deleteFavorite(user.toEntity())
        } else {
            favoriteRepository.insertFavorite(user.toEntity())
        }
    }

    private fun ResponseDetailUser.toEntity(): FavoriteUserEntity =
        FavoriteUserEntity(this.username, this.avatarUrl)

    override fun onCleared() {
        super.onCleared()
        _detailUser.value = null
        _followers.value = null
        _followings.value = null
        _repositories.value = null
    }

}