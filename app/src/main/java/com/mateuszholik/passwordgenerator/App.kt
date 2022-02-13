package com.mateuszholik.passwordgenerator

import android.app.Application
import com.mateuszholik.passwordgenerator.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(allModules)
        }
    }
}