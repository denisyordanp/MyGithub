package com.denisyordanp.mygithub

import android.content.res.Resources

object UserData {

    fun getUsers(resources: Resources): List<User> {
        val dataName = resources.getStringArray(R.array.name)
        val dataUsername = resources.getStringArray(R.array.username)
        val dataLocation = resources.getStringArray(R.array.location)
        val dataRepository = resources.getStringArray(R.array.repository)
        val dataCompany = resources.getStringArray(R.array.company)
        val dataFollowers = resources.getStringArray(R.array.followers)
        val dataFollowing = resources.getStringArray(R.array.following)
        val dataAvatar = resources.obtainTypedArray(R.array.avatar)
        val listUser = mutableListOf<User>()
        dataName.forEachIndexed { index, name ->
            val user = User(
                dataAvatar.getResourceId(index, -1),
                dataCompany[index],
                dataFollowers[index].toInt(),
                dataFollowing[index].toInt(),
                dataLocation[index],
                name,
                dataRepository[index].toInt(),
                dataUsername[index]
            )
            listUser.add(user)
        }
        dataAvatar.recycle()
        return listUser
    }
}