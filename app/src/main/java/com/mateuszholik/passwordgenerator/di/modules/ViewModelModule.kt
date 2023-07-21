package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.NOTIFICATION_WORK_SCHEDULER
import com.mateuszholik.passwordgenerator.ui.MainViewModel
import com.mateuszholik.passwordgenerator.ui.createpin.CreatePinViewModel
import com.mateuszholik.passwordgenerator.ui.editpassword.EditPasswordViewModel
import com.mateuszholik.passwordgenerator.ui.export.ExportPasswordsViewModel
import com.mateuszholik.passwordgenerator.ui.generatepassword.GeneratePasswordViewModel
import com.mateuszholik.passwordgenerator.ui.imports.ImportPasswordsViewModel
import com.mateuszholik.passwordgenerator.ui.login.LogInViewModel
import com.mateuszholik.passwordgenerator.ui.logintransition.LoginTransitionViewModel
import com.mateuszholik.passwordgenerator.ui.migration.MigrationViewModel
import com.mateuszholik.passwordgenerator.ui.passworddetails.PasswordDetailsViewModel
import com.mateuszholik.passwordgenerator.ui.passwords.PasswordsViewModel
import com.mateuszholik.passwordgenerator.ui.passwordscore.PasswordScoreViewModel
import com.mateuszholik.passwordgenerator.ui.savepassword.SavePasswordViewModel
import com.mateuszholik.passwordgenerator.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        LoginTransitionViewModel(isPinCreatedUseCase = get())
    }

    viewModel {
        CreatePinViewModel(
            isPinCorrectToSaveUseCase = get(),
            savePinUseCase = get(),
            textProvider = get(),
        )
    }

    viewModel {
        LogInViewModel(
            isPinCorrectUseCase = get(),
            shouldUseBiometricAuthenticationUseCase = get(),
            textProvider = get(),
        )
    }

    viewModel {
        GeneratePasswordViewModel(
            createPasswordUseCase = get(),
            textProvider = get()
        )
    }

    viewModel { (password: String) ->
        PasswordScoreViewModel(
            password = password,
            validatePasswordUseCase = get(),
            textProvider = get()
        )
    }

    viewModel { (password: String?) ->
        SavePasswordViewModel(
            generatedPassword = password,
            getPasswordUseCase = get(),
            insertPasswordAndGetIdUseCase = get(),
            workScheduler = get(named(NOTIFICATION_WORK_SCHEDULER)),
            textProvider = get()
        )
    }

    viewModel {
        PasswordsViewModel(
            getPasswordsUseCase = get(),
            textProvider = get()
        )
    }

    viewModel { (passwordId: Long) ->
        PasswordDetailsViewModel(
            passwordId = passwordId,
            getPasswordUseCase = get(),
            deletePasswordUseCase = get(),
            clipboardManager = get(),
            workScheduler = get(named(NOTIFICATION_WORK_SCHEDULER)),
            validatePasswordUseCase = get(),
            textProvider = get()
        )
    }

    viewModel { (passwordInfo: PasswordInfo) ->
        EditPasswordViewModel(
            passwordInfo = passwordInfo,
            updatePasswordUseCase = get(),
            workScheduler = get(named(NOTIFICATION_WORK_SCHEDULER)),
            textProvider = get()
        )
    }

    viewModel {
        SettingsViewModel(
            saveIfShouldUseBiometricAuthenticationUseCase = get(),
            shouldUseBiometricAuthenticationUseCase = get(),
            savePasswordValidityValueUseCase = get(),
            textProvider = get()
        )
    }

    viewModel {
        ExportPasswordsViewModel(
            exportPasswordsUseCase = get()
        )
    }

    viewModel {
        ImportPasswordsViewModel(importPasswordsUseCase = get())
    }

    viewModel { MainViewModel() }

    viewModel {
        MigrationViewModel(
            getMigrationStateUseCase = get(),
            migrateDataToTheCorrectStateUseCase = get(),
            saveMigrationStateUseCase = get()
        )
    }
}
