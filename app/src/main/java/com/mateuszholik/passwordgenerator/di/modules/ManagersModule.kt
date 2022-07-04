package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.managers.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val managersModule = module {

    single<ClipboardManager> {
        ClipboardManagerImpl(
            context = androidContext(),
            messageProvider = get(named(TOAST_MESSAGE_PROVIDER))
        )
    }

    single<NotificationManager> {
        NotificationManagerImpl(context = androidContext())
    }

    factory { BiometricManager(biometricPromptFactory = get()) }
}