package com.tashevv.poplibsgithub

import android.app.Application
import android.content.Context
import com.tashevv.poplibsgithub.dependencyInjection.AppComponent
import com.tashevv.poplibsgithub.dependencyInjection.DaggerAppComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        private lateinit var context: Context
        fun getAppContext(): Context {
            return context
        }
    }

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        appComponent = DaggerAppComponent.create()
//        appComponent.getUsersRepo()
    }
}

val Context.app: App get() = applicationContext as App


//        startKoin {
//            androidLogger()
//            androidContext(this@App)
//            modules(appModule)
//        }