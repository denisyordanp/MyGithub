package com.denisyordanp.mygithub.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.denisyordanp.mygithub.R
import com.denisyordanp.mygithub.data.remote.ApiConfig
import com.denisyordanp.mygithub.models.remote.ResponseDetailUser
import com.denisyordanp.mygithub.models.remote.ResponseFollowUsers
import com.denisyordanp.mygithub.models.remote.ResponseRepository
import com.denisyordanp.mygithub.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    // DATA
    private val _detailUser = MutableLiveData<ResponseDetailUser>()
    val detailUser: LiveData<ResponseDetailUser> = _detailUser

    private val _followers = MutableLiveData<List<ResponseFollowUsers>>()
    val followers: LiveData<List<ResponseFollowUsers>> = _followers

    private val _followings = MutableLiveData<List<ResponseFollowUsers>>()
    val followings: LiveData<List<ResponseFollowUsers>> = _followings

    private val _repositories = MutableLiveData<List<ResponseRepository>>()
    val repositories: LiveData<List<ResponseRepository>> = _repositories

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

    fun requestUserData(username: String) {
        username.apply {
            requestDetailUser()
            requestFollowers()
            requestFollowings()
            requestRepositories()
        }
    }

    private fun String.requestDetailUser() {
        _isDetailUserLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(this)
        client.enqueue(object : Callback<ResponseDetailUser> {
            override fun onResponse(
                call: Call<ResponseDetailUser>,
                response: Response<ResponseDetailUser>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _detailUser.value = responseBody
                } else {
                    _showSnackEvent.value = Event(R.string.server_error)
                }
                _isDetailUserLoading.value = false
            }

            override fun onFailure(call: Call<ResponseDetailUser>, t: Throwable) {
                _isDetailUserLoading.value = false
                _showSnackEvent.value = Event(R.string.request_error)
            }
        })
    }

    private fun String.requestFollowers() {
        _isFollowersLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(this)
        client.enqueue(object : Callback<List<ResponseFollowUsers>> {
            override fun onResponse(
                call: Call<List<ResponseFollowUsers>>,
                response: Response<List<ResponseFollowUsers>>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _followers.value = responseBody
                } else {
                    _showSnackEvent.value = Event(R.string.server_error)
                }
                _isFollowersLoading.value = false
            }

            override fun onFailure(call: Call<List<ResponseFollowUsers>>, t: Throwable) {
                _isFollowersLoading.value = false
                _showSnackEvent.value = Event(R.string.request_error)
            }
        })
    }

    private fun String.requestFollowings() {
        _isFollowingsLoading.value = true
        val client = ApiConfig.getApiService().getFollowings(this)
        client.enqueue(object : Callback<List<ResponseFollowUsers>> {
            override fun onResponse(
                call: Call<List<ResponseFollowUsers>>,
                response: Response<List<ResponseFollowUsers>>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _followings.value = responseBody
                } else {
                    _showSnackEvent.value = Event(R.string.server_error)
                }
                _isFollowingsLoading.value = false
            }

            override fun onFailure(call: Call<List<ResponseFollowUsers>>, t: Throwable) {
                _isFollowingsLoading.value = false
                _showSnackEvent.value = Event(R.string.request_error)
            }
        })
    }

    private fun String.requestRepositories() {
        _isRepositoriesLoading.value = true
        val client = ApiConfig.getApiService().getRepositories(this)
        client.enqueue(object : Callback<List<ResponseRepository>> {
            override fun onResponse(
                call: Call<List<ResponseRepository>>,
                response: Response<List<ResponseRepository>>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _repositories.value = responseBody
                } else {
                    _showSnackEvent.value = Event(R.string.server_error)
                }
                _isRepositoriesLoading.value = false
            }

            override fun onFailure(call: Call<List<ResponseRepository>>, t: Throwable) {
                _isRepositoriesLoading.value = false
                _showSnackEvent.value = Event(R.string.request_error)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        _detailUser.value = null
        _followers.value = null
        _followings.value = null
        _repositories.value = null
    }

}