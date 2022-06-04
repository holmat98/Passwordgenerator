package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.NOTIFICATION_WORK_SCHEDULER
import com.mateuszholik.passwordgenerator.ui.authentication.AuthenticationHostViewModel
import com.mateuszholik.passwordgenerator.ui.authentication.createpin.CreatePinViewModel
import com.mateuszholik.passwordgenerator.ui.authentication.login.LogInViewModel
import com.mateuszholik.passwordgenerator.ui.editpassword.EditPasswordViewModel
import com.mateuszholik.passwordgenerator.ui.generatepassword.GeneratePasswordViewModel
import com.mateuszholik.passwordgenerator.ui.passworddetails.PasswordDetailsViewModel
import com.mateuszholik.passwordgenerator.ui.passwords.PasswordsViewModel
import com.mateuszholik.passwordgenerator.ui.passwordscore.PasswordScoreViewModel
import com.mateuszholik.passwordgenerator.ui.passwordvalidationresult.PasswordValidationResultViewModel
import com.mateuszholik.passwordgenerator.ui.savepassword.SavePasswordViewModel
import com.mateuszholik.passwordgenerator.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        CreatePinViewModel(createPinUseCase = get())
    }

    viewModel {
        LogInViewModel(
            isPinCorrectUseCase = get(),
            shouldUseBiometricAuthenticationUseCase = get()
        )
    }

    viewModel {
        AuthenticationHostViewModel(shouldSkipOnBoardingUseCase = get())
    }

    viewModel {
        GeneratePasswordViewModel(createPasswordUseCase = get())
    }

    viewModel { (password: String) ->
        PasswordScoreViewModel(
            password = password,
            calculatePasswordScoreUseCase = get()
        )
    }

    viewModel { (password: String?) ->
        SavePasswordViewModel(
            generatedPassword = password,
            savePasswordUseCase = get()
        )
    }

    viewModel {
        PasswordsViewModel(
            getPasswordsUseCase = get(),
            passwordsScoreManager = get()
        )
    }

    viewModel { (password: String) ->
        PasswordValidationResultViewModel(
            password = password,
            validatePasswordUseCase = get()
        )
    }

    viewModel { (password: Password) ->
        PasswordDetailsViewModel(
            password = password,
            calculatePasswordScoreUseCase = get(),
            deletePasswordUseCase = get(),
            clipboardManager = get(),
            workScheduler = get(named(NOTIFICATION_WORK_SCHEDULER))
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
            shouldUseBiometricAuthenticationUseCase = get()
        )
    }
}