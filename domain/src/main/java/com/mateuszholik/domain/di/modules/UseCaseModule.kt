package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.usecase.CreatePinUseCase
import com.mateuszholik.domain.usecase.CreatePinUseCaseImpl
import org.koin.dsl.module

internal val useCaseModule = module {

    factory<CreatePinUseCase> {
        CreatePinUseCaseImpl(get())
    }
}