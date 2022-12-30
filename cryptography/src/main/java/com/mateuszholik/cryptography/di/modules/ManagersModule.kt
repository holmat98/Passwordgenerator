package com.mateuszholik.cryptography.di.modules

import com.mateuszholik.cryptography.CryptographyKeyManager
import com.mateuszholik.cryptography.CryptographyKeyManagerImpl
import com.mateuszholik.cryptography.EncryptionManager
import com.mateuszholik.cryptography.EncryptionManagerImpl
import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.KeyBaseEncryptionManagerImpl
import com.mateuszholik.cryptography.PasswordBaseEncryptionManager
import com.mateuszholik.cryptography.PasswordBaseEncryptionManagerImpl
import org.koin.dsl.module

internal val managersModule = module {

    factory<CryptographyKeyManager> { CryptographyKeyManagerImpl() }

    factory<EncryptionManager> { EncryptionManagerImpl() }

    factory<KeyBaseEncryptionManager> {
        KeyBaseEncryptionManagerImpl(
            cryptographyKeyManager = get(),
            encryptionManager = get()
        )
    }

    factory<PasswordBaseEncryptionManager> {
        PasswordBaseEncryptionManagerImpl(
            secretKeySpecFactory = get(),
            encryptionManager = get()
        )
    }
}