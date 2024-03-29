package com.mateuszholik.passwordvalidation.di.modules

import com.mateuszholik.passwordvalidation.usecases.GetIsPasswordANameUseCase
import com.mateuszholik.passwordvalidation.usecases.GetIsPasswordAPetNameUseCase
import com.mateuszholik.passwordvalidation.usecases.ValidatePasswordUseCase
import com.mateuszholik.passwordvalidation.usecases.ValidatePasswordUseCaseImpl
import org.koin.dsl.module

internal val useCaseModule = module {

    single<ValidatePasswordUseCase> {
        ValidatePasswordUseCaseImpl(
            passwordValidationStrategyProvider = get()
        )
    }

    single {
        GetIsPasswordANameUseCase(
            commonNameDao = get(),
            stringTransformer = get()
        )
    }

    single {
        GetIsPasswordAPetNameUseCase(
            commonPetsNameDao = get(),
            stringTransformer = get()
        )
    }
}