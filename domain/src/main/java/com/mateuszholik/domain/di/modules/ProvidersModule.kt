package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.providers.PasswordScoreProvider
import com.mateuszholik.domain.providers.PasswordScoreProviderImpl
import org.koin.dsl.module

internal val providersModule = module {

    single<PasswordScoreProvider> {
        PasswordScoreProviderImpl(get(), get(), get(), get())
    }
}