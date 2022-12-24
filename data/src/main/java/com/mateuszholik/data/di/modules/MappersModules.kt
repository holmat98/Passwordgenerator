package com.mateuszholik.data.di.modules

import com.mateuszholik.data.mappers.NewPasswordToPasswordDBMapper
import com.mateuszholik.data.mappers.NewPasswordToPasswordDBMapperImpl
import com.mateuszholik.data.mappers.PasswordDBListToPasswordListMapper
import com.mateuszholik.data.mappers.PasswordDBListToPasswordListMapperImpl
import com.mateuszholik.data.mappers.PasswordDBToPasswordMapper
import com.mateuszholik.data.mappers.PasswordDBToPasswordMapperImpl
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordDBMapper
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordDBMapperImpl
import org.koin.dsl.module

internal val mappersModule = module {

    factory<PasswordDBToPasswordMapper> {
        PasswordDBToPasswordMapperImpl(encryptionManager = get())
    }

    factory<PasswordDBListToPasswordListMapper> {
        PasswordDBListToPasswordListMapperImpl(
            passwordDBToPasswordMapper = get()
        )
    }

    factory<NewPasswordToPasswordDBMapper> {
        NewPasswordToPasswordDBMapperImpl(
            encryptionManager = get(),
            sharedPrefManager = get()
        )
    }

    factory<UpdatedPasswordToPasswordDBMapper> {
        UpdatedPasswordToPasswordDBMapperImpl(
            encryptionManager = get(),
            sharedPrefManager = get()
        )
    }
}