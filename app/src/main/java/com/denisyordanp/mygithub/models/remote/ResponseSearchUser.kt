package com.denisyordanp.mygithub.models.remote


import com.google.gson.annotations.SerializedName

data class ResponseSearchUser(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("login")
    val username: String,
)