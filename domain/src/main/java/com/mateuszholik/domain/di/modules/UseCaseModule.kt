package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.usecase.CreatePinUseCase
import com.mateuszholik.domain.usecase.CreatePinUseCaseImpl
import com.mateuszholik.domain.usecase.ShouldSkipOnBoardingUseCase
import com.mateuszholik.domain.usecase.ShouldSkipOnBoardingUseCaseImpl
import com.mateuszholik.domain.usecase.IsPinCorrectUseCase
import com.mateuszholik.domain.usecase.IsPinCorrectUseCaseImpl
import com.mateuszholik.domain.usecase.CreatePasswordUseCase
import com.mateuszholik.domain.usecase.CreatePasswordUseCaseImpl
import com.mateuszholik.domain.usecase.ValidatePasswordUseCase
import com.mateuszholik.domain.usecase.ValidatePasswordUseCaseImpl
import com.mateuszholik.domain.usecase.CalculatePasswordScoreUseCase
import com.mateuszholik.domain.usecase.CalculatePasswordScoreUseCaseImpl
import com.mateuszholik.domain.usecase.SavePasswordUseCase
import com.mateuszholik.domain.usecase.SavePasswordUseCaseImpl
import com.mateuszholik.domain.usecase.GetPasswordsUseCase
import com.mateuszholik.domain.usecase.GetPasswordsUseCaseImpl
import com.mateuszholik.domain.usecase.DeletePasswordUseCase
import com.mateuszholik.domain.usecase.DeletePasswordUseCaseImpl
import com.mateuszholik.domain.usecase.UpdatePasswordUseCase
import com.mateuszholik.domain.usecase.UpdatePasswordUseCaseImpl
import com.mateuszholik.domain.usecase.SaveIfShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.domain.usecase.SaveIfShouldUseBiometricAuthenticationUseCaseImpl
import com.mateuszholik.domain.usecase.ShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.domain.usecase.ShouldUseBiometricAuthenticationUseCaseImpl
import com.mateuszholik.domain.usecase.SavePasswordValidityValueUseCase
import com.mateuszholik.domain.usecase.SavePasswordValidityValueUseCaseImpl
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

    factory<DeletePasswordUseCase> {
        DeletePasswordUseCaseImpl(passwordsRepository = get())
    }

    factory<UpdatePasswordUseCase> {
        UpdatePasswordUseCaseImpl(passwordsRepository = get())
    }

    factory<SaveIfShouldUseBiometricAuthenticationUseCase> {
        SaveIfShouldUseBiometricAuthenticationUseCaseImpl(sharedPrefManager = get())
    }

    factory<ShouldUseBiometricAuthenticationUseCase> {
        ShouldUseBiometricAuthenticationUseCaseImpl(sharedPrefManager = get())
    }

    factory<SavePasswordValidityValueUseCase> {
        SavePasswordValidityValueUseCaseImpl(sharedPrefManager = get())
    }
}