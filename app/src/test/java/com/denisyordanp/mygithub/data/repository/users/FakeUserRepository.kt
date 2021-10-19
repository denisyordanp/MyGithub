package com.denisyordanp.mygithub.data.repository.users

import com.denisyordanp.mygithub.models.remote.ResponseDetailUser
import com.denisyordanp.mygithub.models.remote.ResponseFollowUsers
import com.denisyordanp.mygithub.models.remote.ResponseRepository

class FakeUserRepository : UserRepository {

    private var shouldReturnError = false

    fun shouldReturnError(isError: Boolean) {
        shouldReturnError = isError
    }

    override suspend fun requestUserDetail(username: String): ResponseDetailUser {
        return if (shouldReturnError) {
            throw Exception("error occurred")
        } else {
            fakeDetailUser
        }
    }

    override suspend fun requestFollowers(username: String): List<ResponseFollowUsers> {
        return if (shouldReturnError) {
            throw Exception("error occurred")
        } else {
            fakeFollowUsers
        }
    }

    override suspend fun requestFollowings(username: String): List<ResponseFollowUsers> {
        return if (shouldReturnError) {
            throw Exception("error occurred")
        } else {
            fakeFollowUsers
        }
    }

    override suspend fun requestRepositories(username: String): List<ResponseRepository> {
        return if (shouldReturnError) {
            throw Exception("error occurred")
        } else {
            fakeRepositories
        }
    }

    companion object {
        val fakeDetailUser =
            ResponseDetailUser("avatarUrl", "company", 10, 10, "location", "username", "name", 10)
        val fakeFollowUsers = listOf(ResponseFollowUsers("avatarUrl", "username"))
        val fakeRepositories = listOf(ResponseRepository("description", "name"))
    }
}