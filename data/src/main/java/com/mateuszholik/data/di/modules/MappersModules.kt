package com.mateuszholik.data.di.modules

import com.mateuszholik.data.mappers.AutofillPasswordDetailsViewMapper
import com.mateuszholik.data.mappers.AutofillPasswordDetailsViewMapperImpl
import com.mateuszholik.data.mappers.AutofillPasswordsDetailsViewListMapper
import com.mateuszholik.data.mappers.AutofillPasswordsDetailsViewListMapperImpl
import com.mateuszholik.data.mappers.NewPasswordToNamesEntityMapper
import com.mateuszholik.data.mappers.NewPasswordToNamesEntityMapperImpl
import com.mateuszholik.data.mappers.NewPasswordToPasswordEntityMapper
import com.mateuszholik.data.mappers.NewPasswordToPasswordEntityMapperImpl
import com.mateuszholik.data.mappers.PasswordDBToPasswordMapper
import com.mateuszholik.data.mappers.PasswordDBToPasswordMapperImpl
import com.mateuszholik.data.mappers.PasswordDetailsViewToPasswordDetailsMapper
import com.mateuszholik.data.mappers.PasswordDetailsViewToPasswordDetailsMapperImpl
import com.mateuszholik.data.mappers.PasswordInfoViewListToPasswordInfoListMapper
import com.mateuszholik.data.mappers.PasswordInfoViewListToPasswordInfoListMapperImpl
import com.mateuszholik.data.mappers.PasswordInfoViewToPasswordInfoMapper
import com.mateuszholik.data.mappers.PasswordInfoViewToPasswordInfoMapperImpl
import com.mateuszholik.data.mappers.PasswordsDBListToPasswordsListMapper
import com.mateuszholik.data.mappers.PasswordsDBListToPasswordsListMapperImpl
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordEntityMapper
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordEntityMapperImpl
import com.mateuszholik.data.mappers.UpdatedPasswordToUpdatedNamesMapper
import com.mateuszholik.data.mappers.UpdatedPasswordToUpdatedNamesMapperImpl
import org.koin.dsl.module

internal val mappersModule = module {

    factory<NewPasswordToNamesEntityMapper> {
        NewPasswordToNamesEntityMapperImpl(
            encryptionManager = get()
        )
    }

    factory<NewPasswordToPasswordEntityMapper> {
        NewPasswordToPasswordEntityMapperImpl(
            encryptionManager = get(),
            sharedPrefManager = get()
        )
    }

    factory<PasswordDBToPasswordMapper> {
        PasswordDBToPasswordMapperImpl(
            encryptionManager = get()
        )
    }

    factory<PasswordDetailsViewToPasswordDetailsMapper> {
        PasswordDetailsViewToPasswordDetailsMapperImpl(
            encryptionManager = get()
        )
    }

    factory<PasswordInfoViewListToPasswordInfoListMapper> {
        PasswordInfoViewListToPasswordInfoListMapperImpl(
            passwordInfoViewToPasswordInfoMapper = get()
        )
    }

    factory<PasswordInfoViewToPasswordInfoMapper> {
        PasswordInfoViewToPasswordInfoMapperImpl(encryptionManager = get())
    }

    factory<PasswordsDBListToPasswordsListMapper> {
        PasswordsDBListToPasswordsListMapperImpl(
            passwordDBToPasswordMapper = get()
        )
    }

    factory<UpdatedPasswordToPasswordEntityMapper> {
        UpdatedPasswordToPasswordEntityMapperImpl(
            encryptionManager = get(),
            sharedPrefManager = get()
        )
    }

    factory<UpdatedPasswordToUpdatedNamesMapper> {
        UpdatedPasswordToUpdatedNamesMapperImpl(
            encryptionManager = get()
        )
    }

    factory<AutofillPasswordDetailsViewMapper> {
        AutofillPasswordDetailsViewMapperImpl(
            encryptionManager = get()
        )
    }

    factory<AutofillPasswordsDetailsViewListMapper> {
        AutofillPasswordsDetailsViewListMapperImpl(
            autofillPasswordDetailsViewMapper = get()
        )
    }
}
