package com.denisyordanp.mygithub.data.remote

import com.denisyordanp.mygithub.models.remote.ResponseDetailUser
import com.denisyordanp.mygithub.models.remote.ResponseFollowUsers
import com.denisyordanp.mygithub.models.remote.ResponseRepository
import com.denisyordanp.mygithub.models.remote.ResponseSearchUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/users?")
    fun getSearchUser(
        @Query("q") username: String
    ): Call<ResponseSearchUsers>

    @GET("https://api.github.com/users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): ResponseDetailUser

    @GET("https://api.github.com/users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): List<ResponseFollowUsers>

    @GET("https://api.github.com/users/{username}/following")
    suspend fun getFollowings(
        @Path("username") username: String
    ): List<ResponseFollowUsers>

    @GET("https://api.github.com/users/{username}/repos")
    suspend fun getRepositories(
        @Path("username") username: String
    ): List<ResponseRepository>

}