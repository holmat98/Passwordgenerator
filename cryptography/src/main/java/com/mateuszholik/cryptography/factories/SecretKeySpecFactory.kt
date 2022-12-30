package com.mateuszholik.cryptography.factories

import android.security.keystore.KeyProperties
import com.mateuszholik.cryptography.extensions.adjustLengthAndReturnByteArray
import javax.crypto.spec.SecretKeySpec

internal interface SecretKeySpecFactory {

    fun create(
        password: String,
        algorithm: String = KeyProperties.KEY_ALGORITHM_AES
    ): SecretKeySpec
}

internal class SecretKeySpecFactoryImpl : SecretKeySpecFactory {

    override fun create(password: String, algorithm: String): SecretKeySpec =
        SecretKeySpec(password.adjustLengthAndReturnByteArray(KEY_SIZE), algorithm)

    private companion object {
        const val KEY_SIZE = 32
    }
}