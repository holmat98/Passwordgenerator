package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.NOTIFICATION_PERMISSION
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.managers.BiometricManager
import com.mateuszholik.passwordgenerator.managers.ClipboardManager
import com.mateuszholik.passwordgenerator.managers.ClipboardManagerImpl
import com.mateuszholik.passwordgenerator.managers.NotificationManager
import com.mateuszholik.passwordgenerator.managers.NotificationManagerImpl
import com.mateuszholik.passwordgenerator.managers.permissions.PermissionManager
import com.mateuszholik.passwordgenerator.managers.permissions.notifications.NotificationPermissionManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val managersModule = module {

    single<ClipboardManager> {
        ClipboardManagerImpl(
            context = androidContext(),
            messageProvider = get(named(TOAST_MESSAGE_PROVIDER)),
            textProvider = get()
        )
    }

    single<NotificationManager> {
        NotificationManagerImpl(context = androidContext())
    }

    factory { BiometricManager(biometricPromptFactory = get()) }

    factory<PermissionManager>(named(NOTIFICATION_PERMISSION)) {
        NotificationPermissionManagerImpl(
            sharedPrefManager = get()
        )
    }
}
