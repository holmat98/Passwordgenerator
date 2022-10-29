package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.NOTIFICATION_WORK_SCHEDULER
import com.mateuszholik.passwordgenerator.ui.editpassword.EditPasswordViewModel
import com.mateuszholik.passwordgenerator.ui.generatepassword.GeneratePasswordViewModel
import com.mateuszholik.passwordgenerator.ui.login.LogInViewModel
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
        LogInViewModel(
            isPinCorrectUseCase = get(),
            shouldUseBiometricAuthenticationUseCase = get(),
            isPinCorrectToSaveUseCase = get(),
            savePinUseCase = get(),
            isPinCreatedUseCase = get(),
            stringResToStringMapper = get()
        )
    }

    viewModel {
        GeneratePasswordViewModel(createPasswordUseCase = get())
    }

    viewModel { (password: String) ->
        PasswordScoreViewModel(
            password = password,
            validatePasswordUseCase = get()
        )
    }

    viewModel { (password: String?) ->
        SavePasswordViewModel(
            generatedPassword = password,
            getPasswordUseCase = get(),
            savePasswordUseCase = get(),
            workScheduler = get(named(NOTIFICATION_WORK_SCHEDULER))
        )
    }

    viewModel {
        PasswordsViewModel(
            getPasswordsUseCase = get()
        )
    }

    viewModel { (password: Password) ->
        PasswordDetailsViewModel(
            password = password,
            deletePasswordUseCase = get(),
            clipboardManager = get(),
            workScheduler = get(named(NOTIFICATION_WORK_SCHEDULER)),
            validatePasswordUseCase = get()
        )
    }

    viewModel { (password: Password) ->
        EditPasswordViewModel(
            password = password,
            updatePasswordUseCase = get(),
            workScheduler = get(named(NOTIFICATION_WORK_SCHEDULER))
        )
    }

    viewModel {
        SettingsViewModel(
            saveIfShouldUseBiometricAuthenticationUseCase = get(),
            shouldUseBiometricAuthenticationUseCase = get(),
            savePasswordValidityValueUseCase = get()
        )
    }
}