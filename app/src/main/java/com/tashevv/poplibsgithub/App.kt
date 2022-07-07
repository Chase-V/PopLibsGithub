package com.tashevv.poplibsgithub

import android.app.Application
import android.content.Context
import com.tashevv.poplibsgithub.data.UsersRepoRetrofitImpl
import com.tashevv.poplibsgithub.domain.UsersRepo

class App : Application() {
    val usersRepo: UsersRepo by lazy { UsersRepoRetrofitImpl() }
}

val Context.app: App get() = applicationContext as App
//val Fragment.app: App get() = requireContext().applicationContext as App