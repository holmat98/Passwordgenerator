package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.di.utils.NamedConstants.DOWNLOAD_URI_FACTORY
import com.mateuszholik.domain.usecase.CreatePasswordUseCase
import com.mateuszholik.domain.usecase.CreatePasswordUseCaseImpl
import com.mateuszholik.domain.usecase.DeletePasswordUseCase
import com.mateuszholik.domain.usecase.DeletePasswordUseCaseImpl
import com.mateuszholik.domain.usecase.ExportPasswordsUseCase
import com.mateuszholik.domain.usecase.ExportPasswordsUseCaseImpl
import com.mateuszholik.domain.usecase.GetAutofillPasswordsDetailsUseCase
import com.mateuszholik.domain.usecase.GetAutofillPasswordsDetailsUseCaseImpl
import com.mateuszholik.domain.usecase.GetIfShouldMigrateDataUseCase
import com.mateuszholik.domain.usecase.GetIfShouldMigrateDataUseCaseImpl
import com.mateuszholik.domain.usecase.GetMatchingPasswordsForPackageNameUseCase
import com.mateuszholik.domain.usecase.GetMatchingPasswordsForPackageNameUseCaseImpl
import com.mateuszholik.domain.usecase.GetPasswordScoreUseCase
import com.mateuszholik.domain.usecase.GetPasswordScoreUseCaseImpl
import com.mateuszholik.domain.usecase.GetPasswordUseCase
import com.mateuszholik.domain.usecase.GetPasswordUseCaseImpl
import com.mateuszholik.domain.usecase.GetPasswordValidationResultUseCase
import com.mateuszholik.domain.usecase.GetPasswordValidationResultUseCaseImpl
import com.mateuszholik.domain.usecase.GetPasswordsUseCase
import com.mateuszholik.domain.usecase.GetPasswordsUseCaseImpl
import com.mateuszholik.domain.usecase.ImportPasswordsUseCase
import com.mateuszholik.domain.usecase.ImportPasswordsUseCaseImpl
import com.mateuszholik.domain.usecase.InsertPasswordAndGetIdUseCase
import com.mateuszholik.domain.usecase.InsertPasswordAndGetIdUseCaseImpl
import com.mateuszholik.domain.usecase.IsPinCorrectToSaveUseCase
import com.mateuszholik.domain.usecase.IsPinCorrectToSaveUseCaseImpl
import com.mateuszholik.domain.usecase.IsPinCorrectUseCase
import com.mateuszholik.domain.usecase.IsPinCorrectUseCaseImpl
import com.mateuszholik.domain.usecase.IsPinCreatedUseCase
import com.mateuszholik.domain.usecase.IsPinCreatedUseCaseImpl
import com.mateuszholik.domain.usecase.MigrateDataToTheCorrectStateUseCase
import com.mateuszholik.domain.usecase.MigrateDataToTheCorrectStateUseCaseImpl
import com.mateuszholik.domain.usecase.ReadDataFromFileUseCase
import com.mateuszholik.domain.usecase.ReadDataFromFileUseCaseImpl
import com.mateuszholik.domain.usecase.SaveDataToFileUseCase
import com.mateuszholik.domain.usecase.SaveDataToFileUseCaseImpl
import com.mateuszholik.domain.usecase.SaveIfShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.domain.usecase.SaveIfShouldUseBiometricAuthenticationUseCaseImpl
import com.mateuszholik.domain.usecase.SaveMigrationStateUseCase
import com.mateuszholik.domain.usecase.SaveMigrationStateUseCaseImpl
import com.mateuszholik.domain.usecase.SavePasswordValidityValueUseCase
import com.mateuszholik.domain.usecase.SavePasswordValidityValueUseCaseImpl
import com.mateuszholik.domain.usecase.SavePinUseCase
import com.mateuszholik.domain.usecase.SavePinUseCaseImpl
import com.mateuszholik.domain.usecase.ShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.domain.usecase.ShouldUseBiometricAuthenticationUseCaseImpl
import com.mateuszholik.domain.usecase.UpdatePackageNameUseCase
import com.mateuszholik.domain.usecase.UpdatePackageNameUseCaseImpl
import com.mateuszholik.domain.usecase.UpdatePasswordUseCase
import com.mateuszholik.domain.usecase.UpdatePasswordUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
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
            newPasswordMapper = get(),
            getPasswordScoreUseCase = get()
        )
    }

    single<GetPasswordsUseCase> {
        GetPasswordsUseCaseImpl(
            passwordsRepository = get(),
        )
    }

    factory<DeletePasswordUseCase> {
        DeletePasswordUseCaseImpl(passwordsRepository = get())
    }

    factory<UpdatePasswordUseCase> {
        UpdatePasswordUseCaseImpl(
            passwordsRepository = get(),
            updatedPasswordMapper = get(),
            getPasswordScoreUseCase = get()
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
            passwordsParser = get(),
            saveDataToFileUseCase = get(),
            encryptionManager = get(),
            uriFactory = get(named(DOWNLOAD_URI_FACTORY))
        )
    }

    factory<ReadDataFromFileUseCase> {
        ReadDataFromFileUseCaseImpl(context = androidContext())
    }

    factory<ImportPasswordsUseCase> {
        ImportPasswordsUseCaseImpl(
            readDataFromFileUseCase = get(),
            encryptionManager = get(),
            passwordsParser = get(),
            exportedPasswordsMapper = get(),
            insertPasswordAndGetIdUseCase = get()
        )
    }

    factory<GetPasswordValidationResultUseCase> {
        GetPasswordValidationResultUseCaseImpl(
            validatePasswordUseCase = get()
        )
    }

    factory<GetPasswordScoreUseCase> {
        GetPasswordScoreUseCaseImpl(
            getPasswordValidationResultUseCase = get()
        )
    }

    factory<MigrateDataToTheCorrectStateUseCase> {
        MigrateDataToTheCorrectStateUseCaseImpl(
            migrationRepository = get(),
            getPasswordScoreUseCase = get()
        )
    }

    factory<GetIfShouldMigrateDataUseCase> {
        GetIfShouldMigrateDataUseCaseImpl(
            sharedPrefManager = get()
        )
    }

    factory<SaveMigrationStateUseCase> {
        SaveMigrationStateUseCaseImpl(
            sharedPrefManager = get()
        )
    }

    factory<GetAutofillPasswordsDetailsUseCase> {
        GetAutofillPasswordsDetailsUseCaseImpl(
            passwordsRepository = get()
        )
    }

    factory<UpdatePackageNameUseCase> {
        UpdatePackageNameUseCaseImpl(passwordsRepository = get())
    }

    factory<GetMatchingPasswordsForPackageNameUseCase> {
        GetMatchingPasswordsForPackageNameUseCaseImpl(
            passwordsRepository = get()
        )
    }
}
