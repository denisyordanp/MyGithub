package com.denisyordanp.mygithub.view.search

import androidx.lifecycle.*
import com.denisyordanp.mygithub.R
import com.denisyordanp.mygithub.data.remote.ApiConfig
import com.denisyordanp.mygithub.models.remote.ResponseSearchUser
import com.denisyordanp.mygithub.models.remote.ResponseSearchUsers
import com.denisyordanp.mygithub.utils.Event
import com.denisyordanp.mygithub.utils.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(
    private val preferences: SettingPreferences
) : ViewModel() {

    private val _searchUsers = MutableLiveData<List<ResponseSearchUser>>(emptyList())
    val searchUsers: LiveData<List<ResponseSearchUser>> = _searchUsers

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showSnackEvent = MutableLiveData<Event<Int>>()
    val showSnackEvent: LiveData<Event<Int>> = _showSnackEvent

    fun searchUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUser(username)
        client.enqueue(object : Callback<ResponseSearchUsers> {
            override fun onResponse(
                call: Call<ResponseSearchUsers>,
                response: Response<ResponseSearchUsers>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    if (responseBody.responseSearchUsers.isNullOrEmpty()) {
                        _showSnackEvent.value = Event(R.string.user_not_found)
                    } else {
                        _searchUsers.value = responseBody.responseSearchUsers
                    }
                } else {
                    _showSnackEvent.value = Event(R.string.server_error)
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<ResponseSearchUsers>, t: Throwable) {
                _isLoading.value = false
                _showSnackEvent.value = Event(R.string.request_error)
                t.printStackTrace()
            }
        })
    }

    val isDarkModeActive: LiveData<Boolean> = preferences.getThemeSetting().asLiveData()

    fun changeThemeSetting() {
        viewModelScope.launch {
            val currentDarkModeActive = isDarkModeActive.value == true
            preferences.saveThemeSetting(!currentDarkModeActive)
        }
    }
}