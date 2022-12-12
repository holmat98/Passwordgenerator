package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.usecase.IsPinCreatedUseCase
import com.mateuszholik.domain.usecase.IsPinCreatedUseCaseImpl
import com.mateuszholik.domain.usecase.IsPinCorrectUseCase
import com.mateuszholik.domain.usecase.IsPinCorrectUseCaseImpl
import com.mateuszholik.domain.usecase.CreatePasswordUseCase
import com.mateuszholik.domain.usecase.CreatePasswordUseCaseImpl
import com.mateuszholik.domain.usecase.DeletePasswordUseCase
import com.mateuszholik.domain.usecase.DeletePasswordUseCaseImpl
import com.mateuszholik.domain.usecase.GetPasswordsUseCase
import com.mateuszholik.domain.usecase.GetPasswordsUseCaseImpl
import com.mateuszholik.domain.usecase.GetPasswordUseCase
import com.mateuszholik.domain.usecase.GetPasswordUseCaseImpl
import com.mateuszholik.domain.usecase.IsPinCorrectToSaveUseCase
import com.mateuszholik.domain.usecase.IsPinCorrectToSaveUseCaseImpl
import com.mateuszholik.domain.usecase.SaveIfShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.domain.usecase.SaveIfShouldUseBiometricAuthenticationUseCaseImpl
import com.mateuszholik.domain.usecase.SavePasswordUseCase
import com.mateuszholik.domain.usecase.SavePasswordUseCaseImpl
import com.mateuszholik.domain.usecase.SavePasswordValidityValueUseCase
import com.mateuszholik.domain.usecase.SavePasswordValidityValueUseCaseImpl
import com.mateuszholik.domain.usecase.SavePinUseCase
import com.mateuszholik.domain.usecase.SavePinUseCaseImpl
import com.mateuszholik.domain.usecase.ShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.domain.usecase.ShouldUseBiometricAuthenticationUseCaseImpl
import com.mateuszholik.domain.usecase.UpdatePasswordUseCase
import com.mateuszholik.domain.usecase.UpdatePasswordUseCaseImpl
import org.koin.dsl.module

internal val useCaseModule = module {

    factory<SavePinUseCase> {
        SavePinUseCaseImpl(encryptedSharedPrefManager = get())
    }

    factory<IsPinCreatedUseCase> {
        IsPinCreatedUseCaseImpl(encryptedSharedPrefManager = get())
    }

    factory<IsPinCorrectUseCase> {
        IsPinCorrectUseCaseImpl(encryptedSharedPrefManager = get())
    }

    single<CreatePasswordUseCase> {
        CreatePasswordUseCaseImpl(passwordFactory = get())
    }

    single<SavePasswordUseCase> {
        SavePasswordUseCaseImpl(passwordsRepository = get())
    }

    single<GetPasswordsUseCase> {
        GetPasswordsUseCaseImpl(passwordsRepository = get())
    }

    factory<DeletePasswordUseCase> {
        DeletePasswordUseCaseImpl(passwordsRepository = get())
    }

    factory<UpdatePasswordUseCase> {
        UpdatePasswordUseCaseImpl(passwordsRepository = get())
    }

    single<SaveIfShouldUseBiometricAuthenticationUseCase> {
        SaveIfShouldUseBiometricAuthenticationUseCaseImpl(sharedPrefManager = get())
    }

    single<ShouldUseBiometricAuthenticationUseCase> {
        ShouldUseBiometricAuthenticationUseCaseImpl(sharedPrefManager = get())
    }

    single<SavePasswordValidityValueUseCase> {
        SavePasswordValidityValueUseCaseImpl(sharedPrefManager = get())
    }

    factory<IsPinCorrectToSaveUseCase> { IsPinCorrectToSaveUseCaseImpl(pinValidator = get()) }

    factory<GetPasswordUseCase> {
        GetPasswordUseCaseImpl(passwordsRepository = get())
    }
}