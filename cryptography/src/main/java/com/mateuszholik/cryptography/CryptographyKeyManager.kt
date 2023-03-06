package com.mateuszholik.cryptography

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.mateuszholik.cryptography.utils.CryptographyKeyUtils.BLOCK_MODE
import com.mateuszholik.cryptography.utils.CryptographyKeyUtils.ENCRYPTION_PADDING
import com.mateuszholik.cryptography.utils.CryptographyKeyUtils.KEY_ALGORITHM
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

internal interface CryptographyKeyManager {

    /**
     * Checks if the key is created
     */
    fun isKeyCreated(): Boolean

    /**
     * Creates key
     */
    fun createKey()

    /**
     * @return [SecretKey]
     */
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
            KeyGenerator.getInstance(KEY_ALGORITHM, ANDROID_KEY_STORE)

        val keyGenParamSpec = KeyGenParameterSpec.Builder(
            KEY_NAME,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(BLOCK_MODE)
            .setEncryptionPaddings(ENCRYPTION_PADDING)
            .build()

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
