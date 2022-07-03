package com.tashevv.poplibsgithub.domain

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("id") val id: Long,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl:String,
    @SerializedName("html_url") val htmlUrl:String
    )