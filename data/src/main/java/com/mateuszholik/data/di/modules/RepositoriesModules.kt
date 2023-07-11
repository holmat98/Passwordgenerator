package com.mateuszholik.data.di.modules

import com.mateuszholik.data.repositories.MigrationRepository
import com.mateuszholik.data.repositories.MigrationRepositoryImpl
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
            updatedPasswordToPasswordEntityMapper = get()
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
