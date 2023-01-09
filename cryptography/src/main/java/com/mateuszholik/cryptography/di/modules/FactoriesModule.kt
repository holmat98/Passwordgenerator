package com.mateuszholik.cryptography.di.modules

import com.mateuszholik.cryptography.factories.SecretKeySpecFactory
import com.mateuszholik.cryptography.factories.SecretKeySpecFactoryImpl
import org.koin.dsl.module

internal val factoriesModule = module {

    factory<SecretKeySpecFactory> { SecretKeySpecFactoryImpl() }
}