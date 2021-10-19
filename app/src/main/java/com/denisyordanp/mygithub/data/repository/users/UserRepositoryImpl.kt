package com.denisyordanp.mygithub.data.repository.users

import com.denisyordanp.mygithub.data.remote.GithubService
import com.denisyordanp.mygithub.models.remote.ResponseDetailUser
import com.denisyordanp.mygithub.models.remote.ResponseFollowUsers
import com.denisyordanp.mygithub.models.remote.ResponseRepository

class UserRepositoryImpl(
    private val service: GithubService
) : UserRepository {
    override suspend fun requestUserDetail(username: String): ResponseDetailUser =
        service.getDetailUser(username)

    override suspend fun requestFollowers(username: String): List<ResponseFollowUsers> =
        service.getFollowers(username)

    override suspend fun requestFollowings(username: String): List<ResponseFollowUsers> =
        service.getFollowings(username)

    override suspend fun requestRepositories(username: String): List<ResponseRepository> =
        service.getRepositories(username)
}