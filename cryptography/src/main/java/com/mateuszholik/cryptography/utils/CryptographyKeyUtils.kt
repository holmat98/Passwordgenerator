package com.mateuszholik.cryptography.utils

import android.security.keystore.KeyProperties

internal object CryptographyKeyUtils {

    const val KEY_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
    const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
    const val ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
    const val KEY_CIPHER_TRANSFORMATION = "$KEY_ALGORITHM/$BLOCK_MODE/$ENCRYPTION_PADDING"
}
