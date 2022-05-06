package com.mateuszholik.data.di.modules

import com.mateuszholik.data.mappers.PasswordListMapper
import com.mateuszholik.data.mappers.PasswordListMapperImpl
import com.mateuszholik.data.mappers.PasswordMapper
import com.mateuszholik.data.mappers.PasswordMapperImpl
import org.koin.dsl.module

internal val mappersModule = module {

    factory<PasswordMapper> {
        PasswordMapperImpl(
            encryptionManager = get()
        )
    }

    factory<PasswordListMapper> {
        PasswordListMapperImpl(
            passwordMapper = get()
        )
    }
}