package com.mateuszholik.data.di.modules

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import com.mateuszholik.data.managers.io.EncryptedSharedPrefManagerImpl
import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.data.managers.io.SharedPrefManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val managersModule = module {

    factory<SharedPrefManager> { SharedPrefManagerImpl(androidContext()) }

    factory<EncryptedSharedPrefManager> { EncryptedSharedPrefManagerImpl(androidContext()) }
}