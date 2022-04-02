package com.mateuszholik.data.cryptography

import com.mateuszholik.data.cryptography.models.EncryptedData
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

interface EncryptionManager {

    /**
     * Encrypt data
     *
     * @param value String that will be encrypted
     * @return [EncryptedData][com.mateuszholik.data.cryptography.models.EncryptedData]
     */
    fun encrypt(value: String): EncryptedData

    /**
     * Decrypt data
     *
     * @param encryptedData [EncryptedData][com.mateuszholik.data.cryptography.models.EncryptedData]
     * @return String
     * @throws KeyDoesNotExistsException if key does not exists
     */
    fun decrypt(encryptedData: EncryptedData): String
}

internal class EncryptionManagerImpl(
    private val cryptographyKeyManager: CryptographyKeyManager
) : EncryptionManager {

    override fun encrypt(value: String): EncryptedData {
        if (!cryptographyKeyManager.isKeyCreated()) {
            cryptographyKeyManager.createKey()
        }

        val cipher = Cipher.getInstance(KEY_CIPHER_TRANSFORMATION).also {
            it.init(Cipher.ENCRYPT_MODE, cryptographyKeyManager.getKey())
        }

        val adjustedValue = adjustStringLength(value)

        val iv = cipher.iv
        val encryptedText = cipher.doFinal(adjustedValue.toByteArray(Charsets.UTF_8))

        return EncryptedData(iv, encryptedText)
    }

    override fun decrypt(encryptedData: EncryptedData): String {
        if (!cryptographyKeyManager.isKeyCreated()) {
            throw KeyDoesNotExistsException()
        }

        val ivParameterSpec = IvParameterSpec(encryptedData.iv)
        val cipher = Cipher.getInstance(KEY_CIPHER_TRANSFORMATION).also {
            it.init(
                Cipher.DECRYPT_MODE,
                cryptographyKeyManager.getKey(),
                ivParameterSpec
            )
        }

        return cipher.doFinal().toString(Charsets.UTF_8).trim()
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