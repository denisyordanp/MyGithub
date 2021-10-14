package com.denisyordanp.mygithub.models.remote


import com.google.gson.annotations.SerializedName

data class ResponseSearchUsers(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val responseSearchUsers: List<ResponseSearchUser>,
    @SerializedName("total_count")
    val totalCount: Int
)