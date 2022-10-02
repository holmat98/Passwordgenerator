package com.mateuszholik.passwordvalidation.di.modules

import com.mateuszholik.passwordvalidation.usecases.ValidatePasswordUseCase
import com.mateuszholik.passwordvalidation.usecases.ValidatePasswordUseCaseImpl
import org.koin.dsl.module

internal val useCaseModule = module {

    single<ValidatePasswordUseCase> {
        ValidatePasswordUseCaseImpl(
            passwordValidationStrategyProvider = get()
        )
    }
}