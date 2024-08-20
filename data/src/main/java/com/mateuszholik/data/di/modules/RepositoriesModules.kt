package com.mateuszholik.data.di.modules

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.data.repositories.PasswordsRepositoryImpl
import org.koin.dsl.module

internal val repositoriesModule = module {

    single<PasswordsRepository> {
        PasswordsRepositoryImpl(
            passwordsDao = get(),
            namesDao = get(),
            passwordInfoViewListToPasswordInfoListMapper = get(),
            passwordDetailsViewToPasswordDetailsMapper = get(),
            newPasswordToNamesEntityMapper = get(),
            newPasswordToPasswordEntityMapper = get(),
            updatedPasswordToUpdatedNamesMapper = get(),
            updatedPasswordToPasswordEntityMapper = get(),
            passwordsDBListToPasswordsListMapper = get(),
            autofillPasswordsDetailsViewListMapper = get(),
            encryptionManager = get()
        )
    }
}
