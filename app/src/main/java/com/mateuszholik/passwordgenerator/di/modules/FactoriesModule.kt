package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.factories.*
import org.koin.dsl.module

val factoriesModule = module {

    single<GsonFactory> {
        GsonFactoryImpl(
            localDateTimeSerializer = get(),
            localDateTimeDeserializer = get()
        )
    }

    factory<BiometricPromptFactory> { BiometricPromptFactoryImpl() }

    single<AppBarConfigurationFactory> { AppBarConfigurationFactoryImpl() }

    single<ViewHolderFactory> { ViewHolderFactoryImpl() }
}