package com.tashevv.poplibsgithub.domain.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersListRetrofitImpl {

    private val baseUrl = "https://api.github.com/"

    fun getRetrofitImpl(): UsersListAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .setLenient()
                            .create()
                    )
            )
            .build()
        return retrofit.create(UsersListAPI::class.java)
    }




}