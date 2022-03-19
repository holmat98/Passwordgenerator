package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.ui.createpassword.CreatePasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        CreatePasswordViewModel(get())
    }
}