package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.di.utils.NamedConstants.DOWNLOAD_URI_FACTORY
import com.mateuszholik.domain.factories.DownloadsFolderUriFactoryImpl
import com.mateuszholik.domain.factories.PasswordFactory
import com.mateuszholik.domain.factories.PasswordFactoryImpl
import com.mateuszholik.domain.factories.UriFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val factoriesModule = module {

    single<PasswordFactory> { PasswordFactoryImpl() }

    single<UriFactory>(named(DOWNLOAD_URI_FACTORY)) {
        DownloadsFolderUriFactoryImpl(context = androidContext())
    }
}