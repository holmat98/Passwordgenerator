package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.NOTIFICATION_WORK_SCHEDULER
import com.mateuszholik.passwordgenerator.schedulers.NotificationWorkSchedulerImpl
import com.mateuszholik.passwordgenerator.schedulers.WorkScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val schedulerModule = module {

    single<WorkScheduler>(named(NOTIFICATION_WORK_SCHEDULER)) {
        NotificationWorkSchedulerImpl(
            context = androidContext()
        )
    }
}
