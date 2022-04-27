package com.mateuszholik.data.cryptography

import com.mateuszholik.data.cryptography.Utils.IV_SEPARATOR
import com.mateuszholik.data.cryptography.extensions.toEncryptedData
import com.mateuszholik.data.cryptography.models.EncryptedData
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

internal interface EncryptionManager {

    /**
     * Encrypt data
     *
     * @param value String that will be encrypted
     * @return [EncryptedData][com.mateuszholik.data.cryptography.models.EncryptedData]
     */
    fun encrypt(value: String): String

    /**
     * Decrypt data
     *
     * @param data String
     * @return String
     * @throws KeyDoesNotExistsException if key does not exists
     * @throws IllegalArgumentException if data has wrong format
     */
    fun decrypt(data: String): String
}

internal class EncryptionManagerImpl(
    private val cryptographyKeyManager: CryptographyKeyManager
) : EncryptionManager {

    override fun encrypt(value: String): String {
        if (!cryptographyKeyManager.isKeyCreated()) {
            cryptographyKeyManager.createKey()
        }

        val cipher = Cipher.getInstance(KEY_CIPHER_TRANSFORMATION).also {
            it.init(Cipher.ENCRYPT_MODE, cryptographyKeyManager.getKey())
        }

        val adjustedValue = adjustStringLength(value)

        var encryptionResult = cipher.iv.toString(Charsets.ISO_8859_1) + IV_SEPARATOR
        encryptionResult += cipher.doFinal(adjustedValue.toByteArray(Charsets.UTF_8))
            .toString(Charsets.ISO_8859_1)

        return encryptionResult
    }

    override fun decrypt(data: String): String {
        if (!cryptographyKeyManager.isKeyCreated()) {
            throw KeyDoesNotExistsException()
        }

        val encryptedData = data.toEncryptedData()

        val cipher = Cipher.getInstance(KEY_CIPHER_TRANSFORMATION).also {
            it.init(
                Cipher.DECRYPT_MODE,
                cryptographyKeyManager.getKey(),
                IvParameterSpec(encryptedData.iv)
            )
        }

        return cipher.doFinal(encryptedData.data).toString(Charsets.UTF_8).trim()
    }

    private fun adjustStringLength(value: String): String {
        var temp = value
        while (temp.toByteArray().size % 16 != 0) {
            temp += SPACE_UNI_CODE
        }

        return temp
    }

    private companion object {
        const val KEY_CIPHER_TRANSFORMATION = "AES/CBC/NoPadding"
        const val SPACE_UNI_CODE = "\u0020"
    }
}