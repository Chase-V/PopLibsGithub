package com.tashevv.poplibsgithub.domain.retrofit

import com.tashevv.poplibsgithub.domain.UserEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersListAPI {

    @GET("users")
    fun getUsersList():Call<List<UserEntity>>

}