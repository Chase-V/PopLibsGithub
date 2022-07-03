package com.tashevv.poplibsgithub

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.tashevv.poplibsgithub.data.UsersRepoImpl
import com.tashevv.poplibsgithub.domain.UsersRepo

class App : Application() {
    val usersRepo: UsersRepo by lazy { UsersRepoImpl() }
}

val Context.app: App get() = applicationContext as App
val Fragment.app: App get() = requireContext().applicationContext as App