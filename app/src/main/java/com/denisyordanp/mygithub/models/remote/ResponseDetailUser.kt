package com.denisyordanp.mygithub.models.remote


import com.google.gson.annotations.SerializedName

data class ResponseDetailUser(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("login")
    val username: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("public_repos")
    val publicRepos: Int,
)