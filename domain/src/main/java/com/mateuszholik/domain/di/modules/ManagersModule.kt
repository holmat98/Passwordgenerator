package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.managers.PasswordScoreManager
import com.mateuszholik.domain.managers.PasswordScoreManagerImpl
import org.koin.dsl.module

internal val managersModule = module {

    factory<PasswordScoreManager> {
        PasswordScoreManagerImpl(get(), get(), get(), get())
    }
}