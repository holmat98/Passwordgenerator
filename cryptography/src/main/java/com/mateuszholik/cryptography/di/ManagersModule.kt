package com.mateuszholik.cryptography.di

import com.mateuszholik.cryptography.CryptographyKeyManager
import com.mateuszholik.cryptography.CryptographyKeyManagerImpl
import com.mateuszholik.cryptography.EncryptionManager
import com.mateuszholik.cryptography.EncryptionManagerImpl
import org.koin.dsl.module

internal val managersModule = module {

    factory<CryptographyKeyManager> { CryptographyKeyManagerImpl() }

    factory<EncryptionManager> { EncryptionManagerImpl(cryptographyKeyManager = get()) }
}