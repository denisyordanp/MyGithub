package com.denisyordanp.mygithub.data.repository.users

import com.denisyordanp.mygithub.models.remote.ResponseDetailUser
import com.denisyordanp.mygithub.models.remote.ResponseFollowUsers
import com.denisyordanp.mygithub.models.remote.ResponseRepository

interface UserRepository {
    suspend fun requestUserDetail(username: String): ResponseDetailUser
    suspend fun requestFollowers(username: String): List<ResponseFollowUsers>
    suspend fun requestFollowings(username: String): List<ResponseFollowUsers>
    suspend fun requestRepositories(username: String): List<ResponseRepository>
}