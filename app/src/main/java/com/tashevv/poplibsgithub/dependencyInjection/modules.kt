package com.tashevv.poplibsgithub.dependencyInjection

import com.google.gson.GsonBuilder
import com.tashevv.poplibsgithub.data.RetrofitUsersRepoImpl
import com.tashevv.poplibsgithub.data.RoomUsersRepoImpl
import com.tashevv.poplibsgithub.data.UsersRepoImpl
import com.tashevv.poplibsgithub.domain.UsersListViewModel
import com.tashevv.poplibsgithub.domain.UsersRepo
import com.tashevv.poplibsgithub.domain.retrofit.UsersListAPI
import com.tashevv.poplibsgithub.domain.room.RoomUsersListDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single<String>(named("baseUrl")) {
        "https://api.github.com/"
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(get<String>(named("baseUrl")))
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
    }

    single<UsersListAPI> {
        get<Retrofit>().create(UsersListAPI::class.java)
    }

    single<UsersRepo>() {
        UsersRepoImpl(get(), get())
    }

    single<RetrofitUsersRepoImpl> {
        RetrofitUsersRepoImpl(get())
    }

    single<RoomUsersRepoImpl> {
        RoomUsersRepoImpl(
            RoomUsersListDatabase.getDatabaseInstance(get())
        )
    }

    viewModel { UsersListViewModel(get()) }
}