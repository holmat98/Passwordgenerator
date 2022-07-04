package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.factories.PasswordFactory
import com.mateuszholik.domain.factories.PasswordFactoryImpl
import org.koin.dsl.module

internal val factoriesModule = module {

    single<PasswordFactory> { PasswordFactoryImpl() }
}