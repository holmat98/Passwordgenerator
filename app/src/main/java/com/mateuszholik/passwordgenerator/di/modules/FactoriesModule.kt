package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.factories.AppBarConfigurationFactory
import com.mateuszholik.passwordgenerator.factories.AppBarConfigurationFactoryImpl
import com.mateuszholik.passwordgenerator.factories.BiometricPromptFactory
import com.mateuszholik.passwordgenerator.factories.BiometricPromptFactoryImpl
import org.koin.dsl.module

val factoriesModule = module {

    factory<BiometricPromptFactory> { BiometricPromptFactoryImpl() }

    single<AppBarConfigurationFactory> { AppBarConfigurationFactoryImpl() }
}
