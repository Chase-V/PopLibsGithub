package com.tashevv.poplibsgithub.data

import com.google.gson.GsonBuilder
import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.domain.UsersRepo
import com.tashevv.poplibsgithub.domain.retrofit.UsersListAPI
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitUsersRepoImpl : UsersRepo {

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
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        return retrofit.create(UsersListAPI::class.java)
    }

    override fun getUsers(): Single<List<UserEntity>> = getRetrofitImpl().getUsersList()

    override fun insertUser(userEntity: UserEntity): Completable {
        return Completable.complete()
    }


}


/*  Без rxjava
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

//        C RxJava
        getRetrofitImpl().getUsersList().subscribeBy(
            onSuccess = { onSuccess.invoke(it) },
            onError = {
                onSuccess.invoke(localData)
                onError?.invoke(it)
            }
        )
    }*/