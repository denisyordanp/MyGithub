package com.denisyordanp.mygithub.models.remote


import com.google.gson.annotations.SerializedName

data class ResponseRepository(
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String,
)