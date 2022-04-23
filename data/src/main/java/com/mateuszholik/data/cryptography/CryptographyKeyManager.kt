package com.mateuszholik.data.cryptography

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

internal interface CryptographyKeyManager {

    fun isKeyCreated(): Boolean
    fun createKey()
    fun getKey(): SecretKey
}

internal class CryptographyKeyManagerImpl : CryptographyKeyManager {

    override fun isKeyCreated(): Boolean {
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE).also { it.load(null) }
        val secretKeyEntry = keyStore.getEntry(KEY_NAME, null) as? KeyStore.SecretKeyEntry
        val secretKey = secretKeyEntry?.secretKey

        return secretKey != null
    }

    override fun createKey() {
        val keyGenerator =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)

        val keyGenParamSpec = KeyGenParameterSpec.Builder(
            KEY_NAME,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).apply {
            setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        }.build()

        keyGenerator.run {
            init(keyGenParamSpec)
            generateKey()
        }
    }

    override fun getKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE).also { it.load(null) }
        val secretKeyEntry = keyStore.getEntry(KEY_NAME, null) as KeyStore.SecretKeyEntry

        return secretKeyEntry.secretKey
    }

    private companion object {
        const val ANDROID_KEY_STORE = "AndroidKeyStore"
        const val KEY_NAME = "SAVED_PASSWORDS_KEY"
    }
}