package com.tashevv.poplibsgithub.dependencyInjection

import com.google.gson.GsonBuilder
import com.tashevv.poplibsgithub.App
import com.tashevv.poplibsgithub.data.RetrofitUsersRepoImpl
import com.tashevv.poplibsgithub.data.RoomUsersRepoImpl
import com.tashevv.poplibsgithub.data.UsersRepoImpl
import com.tashevv.poplibsgithub.domain.UsersRepo
import com.tashevv.poplibsgithub.domain.retrofit.UsersListAPI
import com.tashevv.poplibsgithub.domain.room.RoomUsersListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val baseUrl: String = "https://api.github.com/"

    @Provides
    @Singleton
    fun provideUsersRepo(retrofit: RetrofitUsersRepoImpl, room: RoomUsersRepoImpl): UsersRepo {
        return UsersRepoImpl(retrofit, room)
    }

    @Provides
    @Singleton
    fun provideRetrofitUsersRepoImpl(api: UsersListAPI): RetrofitUsersRepoImpl {
        return RetrofitUsersRepoImpl(api)
    }

    @Provides
    @Singleton
    fun provideUsersListAPI(retrofit: Retrofit): UsersListAPI {
        return retrofit.create(UsersListAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
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
    }

    @Provides
    @Singleton
    fun provideRoomUsersRepoImpl(dbInstance: RoomUsersListDatabase): RoomUsersRepoImpl {
        return RoomUsersRepoImpl(dbInstance)
    }

    @Provides
    @Singleton
    fun provideDbInstance(): RoomUsersListDatabase {
        return RoomUsersListDatabase.getDatabaseInstance(App.getAppContext())
    }


}