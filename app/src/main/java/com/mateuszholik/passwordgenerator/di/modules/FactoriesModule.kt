package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.factories.*
import com.mateuszholik.passwordgenerator.ui.authentication.factories.FragmentFactory
import com.mateuszholik.passwordgenerator.ui.authentication.factories.FragmentFactoryImpl
import org.koin.dsl.module

val factoriesModule = module {

    factory<FragmentFactory> { FragmentFactoryImpl() }

    single<GsonFactory> {
        GsonFactoryImpl(
            localDateTimeSerializer = get(),
            localDateTimeDeserializer = get()
        )
    }

    factory<BiometricPromptFactory> { BiometricPromptFactoryImpl() }

    factory<AppBarConfigurationFactory> {
        AppBarConfigurationFactoryImpl()
    }
}