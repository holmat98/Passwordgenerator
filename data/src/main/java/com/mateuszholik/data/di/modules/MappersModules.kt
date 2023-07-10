package com.mateuszholik.data.di.modules

import com.mateuszholik.data.mappers.NewPasswordToPasswordDBMapper
import com.mateuszholik.data.mappers.NewPasswordToPasswordDBMapperImpl
import com.mateuszholik.data.mappers.NewPasswordsListToPasswordDBListMapper
import com.mateuszholik.data.mappers.NewPasswordsListToPasswordDBListMapperImpl
import com.mateuszholik.data.mappers.PasswordInfoViewListToPasswordInfoListMapper
import com.mateuszholik.data.mappers.PasswordInfoViewListToPasswordInfoListMapperImpl
import com.mateuszholik.data.mappers.PasswordInfoViewToPasswordInfoMapper
import com.mateuszholik.data.mappers.PasswordInfoViewToPasswordInfoMapperImpl
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordDBMapper
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordDBMapperImpl
import org.koin.dsl.module

internal val mappersModule = module {

    factory<PasswordInfoViewToPasswordInfoMapper> {
        PasswordInfoViewToPasswordInfoMapperImpl(encryptionManager = get())
    }

    factory<PasswordInfoViewListToPasswordInfoListMapper> {
        PasswordInfoViewListToPasswordInfoListMapperImpl(
            passwordInfoViewToPasswordInfoMapper = get()
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

    factory<NewPasswordsListToPasswordDBListMapper> {
        NewPasswordsListToPasswordDBListMapperImpl(
            newPasswordToPasswordDBMapper = get()
        )
    }
}
