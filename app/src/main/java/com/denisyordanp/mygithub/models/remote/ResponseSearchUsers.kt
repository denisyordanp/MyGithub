package com.denisyordanp.mygithub.models.remote


import com.google.gson.annotations.SerializedName

data class ResponseSearchUsers(
    @SerializedName("items")
    val responseSearchUsers: List<ResponseSearchUser>,
)