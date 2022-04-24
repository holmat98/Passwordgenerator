package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.ui.authentication.AuthenticationHostViewModel
import com.mateuszholik.passwordgenerator.ui.authentication.createpin.CreatePinViewModel
import com.mateuszholik.passwordgenerator.ui.authentication.login.LogInViewModel
import com.mateuszholik.passwordgenerator.ui.loggeduser.generatepassword.GeneratePasswordViewModel
import com.mateuszholik.passwordgenerator.ui.loggeduser.passwordscore.PasswordScoreViewModel
import com.mateuszholik.passwordgenerator.ui.loggeduser.savepassword.SavePasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        CreatePinViewModel(createPinUseCase = get())
    }

    viewModel {
        LogInViewModel(isPinCorrectUseCase = get())
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
            validatePasswordUseCase = get()
        )
    }

    viewModel { (password: String?) ->
        SavePasswordViewModel(
            generatedPassword = password,
            savePasswordUseCase = get()
        )
    }
}