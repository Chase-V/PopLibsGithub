package com.tashevv.poplibsgithub.domain.retrofit

import com.tashevv.poplibsgithub.domain.UserEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface UsersListAPI {

    @GET("users")
    fun getUsersList(): Single<List<UserEntity>>

}