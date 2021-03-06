package com.mateuszholik.passwordgenerator

import android.app.Application
import com.mateuszholik.passwordgenerator.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@App)
            modules(allModules)
        }
    }
}