package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.mappers.ExportedPasswordToNewPasswordMapper
import com.mateuszholik.domain.mappers.ExportedPasswordToNewPasswordMapperImpl
import com.mateuszholik.domain.mappers.ExportedPasswordsListToNewPasswordsListMapper
import com.mateuszholik.domain.mappers.ExportedPasswordsListToNewPasswordsListMapperImpl
import com.mateuszholik.domain.mappers.NewPasswordMapper
import com.mateuszholik.domain.mappers.NewPasswordMapperImpl
import com.mateuszholik.domain.mappers.PasswordToExportedPasswordMapper
import com.mateuszholik.domain.mappers.PasswordToExportedPasswordMapperImpl
import com.mateuszholik.domain.mappers.PasswordsListToExportPasswordsListMapper
import com.mateuszholik.domain.mappers.PasswordsListToExportPasswordsListMapperImpl
import com.mateuszholik.domain.mappers.UpdatedPasswordMapper
import com.mateuszholik.domain.mappers.UpdatedPasswordMapperImpl
import org.koin.dsl.module

internal val mappersModule = module {

    single<NewPasswordMapper> { NewPasswordMapperImpl() }

    single<UpdatedPasswordMapper> { UpdatedPasswordMapperImpl() }

    single<PasswordToExportedPasswordMapper> {
        PasswordToExportedPasswordMapperImpl()
    }

    single<PasswordsListToExportPasswordsListMapper> {
        PasswordsListToExportPasswordsListMapperImpl(
            passwordToExportedPasswordMapper = get()
        )
    }

    single<ExportedPasswordToNewPasswordMapper> {
        ExportedPasswordToNewPasswordMapperImpl()
    }

    single<ExportedPasswordsListToNewPasswordsListMapper> {
        ExportedPasswordsListToNewPasswordsListMapperImpl(
            exportedPasswordToNewPasswordMapper = get()
        )
    }
}
