package com.tashevv.poplibsgithub.domain.retrofit

import com.tashevv.poplibsgithub.domain.UserEntity
import retrofit2.Call
import retrofit2.http.GET

interface UsersListAPI {

    @GET("users")
    fun getUsersList(): Call<List<UserEntity>>

}