package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.mappers.PasswordValidationTypeToTextMapper
import com.mateuszholik.passwordgenerator.mappers.PasswordValidationTypeToTextMapperImpl
import com.mateuszholik.passwordgenerator.mappers.StringResToStringMapper
import com.mateuszholik.passwordgenerator.mappers.StringResToStringMapperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mappersModule = module {

    single<PasswordValidationTypeToTextMapper> {
        PasswordValidationTypeToTextMapperImpl(androidContext())
    }

    single<StringResToStringMapper> {
        StringResToStringMapperImpl(androidContext())
    }
}