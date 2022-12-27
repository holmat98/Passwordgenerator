package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.mappers.NewPasswordMapper
import com.mateuszholik.domain.mappers.NewPasswordMapperImpl
import com.mateuszholik.domain.mappers.PasswordsListToPasswordsTypeListMapper
import com.mateuszholik.domain.mappers.PasswordsListToPasswordsTypeListMapperImpl
import com.mateuszholik.domain.mappers.PasswordToPasswordTypeMapper
import com.mateuszholik.domain.mappers.PasswordToPasswordTypeMapperImpl
import com.mateuszholik.domain.mappers.UpdatedPasswordMapper
import com.mateuszholik.domain.mappers.UpdatedPasswordMapperImpl
import org.koin.dsl.module

internal val mappersModule = module {

    single<NewPasswordMapper> { NewPasswordMapperImpl() }

    single<UpdatedPasswordMapper> { UpdatedPasswordMapperImpl() }

    single<PasswordToPasswordTypeMapper> { PasswordToPasswordTypeMapperImpl() }

    single<PasswordsListToPasswordsTypeListMapper> {
        PasswordsListToPasswordsTypeListMapperImpl(
            passwordToPasswordTypeMapper = get()
        )
    }
}