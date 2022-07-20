package com.tashevv.poplibsgithub

import android.app.Application
import android.content.Context
import com.tashevv.poplibsgithub.dependencyInjection.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        private lateinit var context: Context
        private fun getAppContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext


        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }

    }
}

val Context.app: App get() = applicationContext as App