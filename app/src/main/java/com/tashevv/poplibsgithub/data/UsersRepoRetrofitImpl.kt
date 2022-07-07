package com.tashevv.poplibsgithub.data

import com.google.gson.GsonBuilder
import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.domain.UsersRepo
import com.tashevv.poplibsgithub.domain.retrofit.UsersListAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersRepoRetrofitImpl : UsersRepo {

    private val baseUrl = "https://api.github.com/"
    private val localData = UsersRepoLocalImpl().getLocalData()

    private fun getRetrofitImpl(): UsersListAPI {
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

    override fun getUsers(onSuccess: (List<UserEntity>) -> Unit, onError: ((Throwable) -> Unit)?) {
        getRetrofitImpl().getUsersList().enqueue(object : Callback<List<UserEntity>> {

            override fun onResponse(
                call: Call<List<UserEntity>>,
                response: Response<List<UserEntity>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess.invoke(response.body()!!)
                } else {
                    onSuccess.invoke(localData) //Чтобы хоть что-то показывалось
                    onError?.invoke(Throwable(response.errorBody().toString()))
                }
            }

            override fun onFailure(call: Call<List<UserEntity>>, t: Throwable) {
                onError?.invoke(t)
                onSuccess.invoke(localData) //Чтобы хоть что-то показывалось
            }

        })
    }
}