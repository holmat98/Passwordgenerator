package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.workers.NotificationWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

val workersModule = module {

    workerOf(::NotificationWorker)
}
