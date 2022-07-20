package com.tashevv.poplibsgithub

import android.app.Application
import android.content.Context
import com.google.gson.GsonBuilder
import com.tashevv.poplibsgithub.data.RetrofitUsersRepoImpl
import com.tashevv.poplibsgithub.data.RoomUsersRepoImpl
import com.tashevv.poplibsgithub.data.UsersRepoImpl
import com.tashevv.poplibsgithub.domain.UsersRepo
import com.tashevv.poplibsgithub.domain.retrofit.UsersListAPI
import com.tashevv.poplibsgithub.domain.room.RoomUsersListDatabase
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object {
        private lateinit var context: Context
        private fun getAppContext(): Context {
            return context
        }

        fun getDatabaseInstance(): RoomUsersListDatabase =
            RoomUsersListDatabase.getDatabaseInstance(
                getAppContext()
            )
    }

    private lateinit var room : RoomUsersRepoImpl

    private val baseUrl = "https://api.github.com/"

    private val retrofit = Retrofit.Builder()
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

    private val api = retrofit.create(UsersListAPI::class.java)

    private val retrofitImpl = RetrofitUsersRepoImpl(api)

    val usersRepo: UsersRepo by lazy { UsersRepoImpl(retrofitImpl, room) }


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        room = RoomUsersRepoImpl(getDatabaseInstance())
    }
}

val Context.app: App get() = applicationContext as App