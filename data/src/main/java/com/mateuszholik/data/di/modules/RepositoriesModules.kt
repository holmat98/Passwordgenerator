package com.mateuszholik.data.di.modules

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
}
