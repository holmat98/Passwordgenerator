package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.usecase.*
import com.mateuszholik.domain.usecase.CreatePinUseCaseImpl
import com.mateuszholik.domain.usecase.ShouldSkipOnBoardingUseCaseImpl
import org.koin.dsl.module

internal val useCaseModule = module {

    factory<CreatePinUseCase> {
        CreatePinUseCaseImpl(encryptedSharedPrefManager = get())
    }

    factory<ShouldSkipOnBoardingUseCase> {
        ShouldSkipOnBoardingUseCaseImpl(encryptedSharedPrefManager = get())
    }

    factory<IsPinCorrectUseCase> {
        IsPinCorrectUseCaseImpl(encryptedSharedPrefManager = get())
    }

    factory<CreatePasswordUseCase> {
        CreatePasswordUseCaseImpl(passwordFactory = get())
    }

    factory<ValidatePasswordUseCase> {
        ValidatePasswordUseCaseImpl(
            containsLetterValidator = get(),
            containsNumberValidator = get(),
            containsSpecialCharacterValidator = get(),
            containsUpperCaseValidator = get(),
            passwordLengthValidator = get()
        )
    }

    factory<CalculatePasswordScoreUseCase> {
        CalculatePasswordScoreUseCaseImpl(passwordScoreManager = get())
    }

    factory<SavePasswordUseCase> {
        SavePasswordUseCaseImpl(passwordsRepository = get())
    }

    factory<GetPasswordsUseCase> {
        GetPasswordsUseCaseImpl(passwordsRepository = get())
    }
}