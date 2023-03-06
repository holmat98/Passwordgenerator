package com.mateuszholik.passwordgenerator

import android.app.Application
import com.mateuszholik.passwordgenerator.di.allModules
import com.mateuszholik.passwordgenerator.logging.CrashReportingTree
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        startKoin {
            androidContext(this@App)
            modules(allModules)
        }
    }
}
