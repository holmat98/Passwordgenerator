package com.mateuszholik.passwordvalidation.di.modules

import com.mateuszholik.passwordvalidation.providers.PasswordValidationStrategyProvider
import com.mateuszholik.passwordvalidation.providers.PasswordValidationStrategyProviderImpl
import org.koin.dsl.module

internal val providersModules = module {

    single<PasswordValidationStrategyProvider> {
        PasswordValidationStrategyProviderImpl(
            commonPasswordDao = get(),
            commonNameDao = get(),
            commonPetsNameDao = get(),
            commonWordDao = get()
        )
    }
}