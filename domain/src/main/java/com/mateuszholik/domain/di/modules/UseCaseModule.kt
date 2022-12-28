package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.usecase.CreatePasswordUseCase
import com.mateuszholik.domain.usecase.CreatePasswordUseCaseImpl
import com.mateuszholik.domain.usecase.DeletePasswordUseCase
import com.mateuszholik.domain.usecase.DeletePasswordUseCaseImpl
import com.mateuszholik.domain.usecase.ExportPasswordsUseCase
import com.mateuszholik.domain.usecase.ExportPasswordsUseCaseImpl
import com.mateuszholik.domain.usecase.GetPasswordUseCase
import com.mateuszholik.domain.usecase.GetPasswordUseCaseImpl
import com.mateuszholik.domain.usecase.GetPasswordsUseCase
import com.mateuszholik.domain.usecase.GetPasswordsUseCaseImpl
import com.mateuszholik.domain.usecase.InsertPasswordAndGetIdUseCase
import com.mateuszholik.domain.usecase.InsertPasswordAndGetIdUseCaseImpl
import com.mateuszholik.domain.usecase.IsPinCorrectToSaveUseCase
import com.mateuszholik.domain.usecase.IsPinCorrectToSaveUseCaseImpl
import com.mateuszholik.domain.usecase.IsPinCorrectUseCase
import com.mateuszholik.domain.usecase.IsPinCorrectUseCaseImpl
import com.mateuszholik.domain.usecase.IsPinCreatedUseCase
import com.mateuszholik.domain.usecase.IsPinCreatedUseCaseImpl
import com.mateuszholik.domain.usecase.SaveDataToFileUseCase
import com.mateuszholik.domain.usecase.SaveDataToFileUseCaseImpl
import com.mateuszholik.domain.usecase.SaveIfShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.domain.usecase.SaveIfShouldUseBiometricAuthenticationUseCaseImpl
import com.mateuszholik.domain.usecase.SavePasswordValidityValueUseCase
import com.mateuszholik.domain.usecase.SavePasswordValidityValueUseCaseImpl
import com.mateuszholik.domain.usecase.SavePinUseCase
import com.mateuszholik.domain.usecase.SavePinUseCaseImpl
import com.mateuszholik.domain.usecase.ShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.domain.usecase.ShouldUseBiometricAuthenticationUseCaseImpl
import com.mateuszholik.domain.usecase.UpdatePasswordUseCase
import com.mateuszholik.domain.usecase.UpdatePasswordUseCaseImpl
import org.koin.android.ext.koin.androidContext
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

    single<InsertPasswordAndGetIdUseCase> {
        InsertPasswordAndGetIdUseCaseImpl(
            passwordsRepository = get(),
            newPasswordMapper = get()
        )
    }

    single<GetPasswordsUseCase> {
        GetPasswordsUseCaseImpl(
            passwordsRepository = get(),
            passwordsListToPasswordsTypeListMapper = get()
        )
    }

    factory<DeletePasswordUseCase> {
        DeletePasswordUseCaseImpl(passwordsRepository = get())
    }

    factory<UpdatePasswordUseCase> {
        UpdatePasswordUseCaseImpl(
            passwordsRepository = get(),
            updatedPasswordMapper = get()
        )
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

    factory<SaveDataToFileUseCase> {
        SaveDataToFileUseCaseImpl(context = androidContext())
    }

    factory<ExportPasswordsUseCase> {
        ExportPasswordsUseCaseImpl(
            passwordsRepository = get(),
            passwordsListToExportPasswordsListMapper = get(),
            jsonParser = get(),
            saveDataToFileUseCase = get()
        )
    }
}