package com.mateuszholik.passwordvalidation.di.modules

import com.mateuszholik.passwordvalidation.providers.PasswordValidationStrategyProvider
import com.mateuszholik.passwordvalidation.providers.PasswordValidationStrategyProviderImpl
import org.koin.dsl.module

internal val providersModules = module {

    single<PasswordValidationStrategyProvider> {
        PasswordValidationStrategyProviderImpl(
            commonPasswordDao = get(),
            getIsPasswordANameUseCase = get(),
            getIsPasswordAPetNameUseCase = get(),
            commonWordDao = get(),
            stringTransformer = get()
        )
    }
}