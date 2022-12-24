package com.mateuszholik.data.di.modules

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.data.repositories.PasswordsRepositoryImpl
import org.koin.dsl.module

internal val repositoriesModule = module {

    single<PasswordsRepository> {
        PasswordsRepositoryImpl(
            passwordsDao = get(),
            passwordDBListToPasswordListMapper = get(),
            passwordDBToPasswordMapper = get(),
            newPasswordToPasswordDBMapper = get(),
            updatedPasswordToPasswordDBMapper = get()
        )
    }
}