package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.passwordgenerator.ui.authentication.AuthenticationHostViewModel
import com.mateuszholik.passwordgenerator.ui.authentication.createpin.CreatePinViewModel
import com.mateuszholik.passwordgenerator.ui.authentication.login.LogInViewModel
import com.mateuszholik.passwordgenerator.ui.loggeduser.editpassword.EditPasswordViewModel
import com.mateuszholik.passwordgenerator.ui.loggeduser.generatepassword.GeneratePasswordViewModel
import com.mateuszholik.passwordgenerator.ui.loggeduser.passworddetails.PasswordDetailsViewModel
import com.mateuszholik.passwordgenerator.ui.loggeduser.passwords.PasswordsViewModel
import com.mateuszholik.passwordgenerator.ui.loggeduser.passwordscore.PasswordScoreViewModel
import com.mateuszholik.passwordgenerator.ui.loggeduser.passwordvalidationresult.PasswordValidationResultViewModel
import com.mateuszholik.passwordgenerator.ui.loggeduser.savepassword.SavePasswordViewModel
import com.mateuszholik.passwordgenerator.ui.loggeduser.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
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
            clipboardManager = get()
        )
    }

    viewModel { (password: Password) ->
        EditPasswordViewModel(
            password = password,
            updatePasswordUseCase = get()
        )
    }

    viewModel {
        SettingsViewModel(
            saveIfShouldUseBiometricAuthenticationUseCase = get(),
            shouldUseBiometricAuthenticationUseCase = get()
        )
    }
}