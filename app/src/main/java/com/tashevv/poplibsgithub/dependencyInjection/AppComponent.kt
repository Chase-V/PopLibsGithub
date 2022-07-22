package com.tashevv.poplibsgithub.dependencyInjection

import com.tashevv.poplibsgithub.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)
//    fun getUsersRepo():UsersRepo

}