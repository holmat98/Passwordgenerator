package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.ui.authentication.AuthenticationHostViewModel
import com.mateuszholik.passwordgenerator.ui.authentication.createpin.CreatePinViewModel
import com.mateuszholik.passwordgenerator.ui.authentication.login.LogInViewModel
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
}