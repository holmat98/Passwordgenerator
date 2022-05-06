package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import com.mateuszholik.passwordgenerator.providers.ToastProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val providersModule = module {

    single<MessageProvider>(named(TOAST_MESSAGE_PROVIDER)) {
        ToastProviderImpl(context = androidContext())
    }
}