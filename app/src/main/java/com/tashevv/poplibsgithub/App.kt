package com.tashevv.poplibsgithub

import android.app.Application
import android.content.Context
import com.tashevv.poplibsgithub.data.UsersRepoImpl
import com.tashevv.poplibsgithub.domain.UsersRepo
import com.tashevv.poplibsgithub.domain.room.RoomUsersListDatabase

class App : Application() {

    companion object {
        private lateinit var context: Context
        fun getAppContext(): Context {
            return context
        }

        fun getDatabaseInstance(): RoomUsersListDatabase =
            RoomUsersListDatabase.getDatabaseInstance(
                getAppContext()
            )
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
    }

    val usersRepo: UsersRepo by lazy { UsersRepoImpl() }


}

val Context.app: App get() = applicationContext as App
//val Fragment.app: App get() = requireContext().applicationContext as App