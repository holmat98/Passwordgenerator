package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.ui.createpin.CreatePinViewModel
import com.mateuszholik.passwordgenerator.ui.login.LogInViewModel
import com.mateuszholik.passwordgenerator.ui.onboarding.OnBoardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        OnBoardingViewModel(shouldSkipOnBoardingUseCase = get())
    }

    viewModel {
        CreatePinViewModel(createPinUseCase = get())
    }

    viewModel {
        LogInViewModel(isPinCorrectUseCase = get())
    }
}