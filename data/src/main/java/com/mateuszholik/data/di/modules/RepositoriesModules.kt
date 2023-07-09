package com.mateuszholik.data.di.modules

import com.mateuszholik.data.repositories.MigrationRepository
import com.mateuszholik.data.repositories.MigrationRepositoryImpl
import com.mateuszholik.data.repositories.OldPasswordsRepository
import com.mateuszholik.data.repositories.OldPasswordsRepositoryImpl
import org.koin.dsl.module

internal val repositoriesModule = module {

    single<OldPasswordsRepository> {
        OldPasswordsRepositoryImpl(
            oldPasswordsDao = get(),
            passwordDBListToPasswordListMapper = get(),
            passwordDBToPasswordMapper = get(),
            newPasswordToPasswordDBMapper = get(),
            newPasswordsListToPasswordDBListMapper = get(),
            updatedPasswordToPasswordDBMapper = get()
        )
    }

    single<MigrationRepository> {
        MigrationRepositoryImpl(
            oldPasswordsDao = get(),
            namesDao = get(),
            passwordsDao = get(),
            encryptionManager = get()
        )
    }
}
