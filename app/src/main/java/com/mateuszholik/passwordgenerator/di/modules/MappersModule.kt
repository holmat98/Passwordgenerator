package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.mappers.PasswordValidationTypeToTextMapper
import com.mateuszholik.passwordgenerator.mappers.PasswordValidationTypeToTextMapperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mappersModule = module {

    single<PasswordValidationTypeToTextMapper> {
        PasswordValidationTypeToTextMapperImpl(androidContext())
    }
}