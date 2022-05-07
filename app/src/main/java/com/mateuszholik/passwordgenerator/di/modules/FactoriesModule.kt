package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.factories.GsonFactory
import com.mateuszholik.passwordgenerator.factories.GsonFactoryImpl
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
}