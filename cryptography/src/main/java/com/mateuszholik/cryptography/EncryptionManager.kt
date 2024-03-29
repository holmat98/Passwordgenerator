package com.mateuszholik.cryptography

import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.cryptography.utils.CryptographyKeyUtils.KEY_CIPHER_TRANSFORMATION
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

interface EncryptionManager {

    /**
     * Encrypt data
     *
     * @param value String that will be encrypted
     * @param key [SecretKey][javax.crypto.SecretKey] that will be used to encrypt data
     * @return [EncryptedData][com.mateuszholik.cryptography.models.EncryptedData]
     */
    fun encrypt(value: String, key: SecretKey): EncryptedData

    /**
     * Decrypt data
     *
     * @param encryptedData [EncryptedData][com.mateuszholik.cryptography.models.EncryptedData]
     * @param key [SecretKey][javax.crypto.SecretKey] that will be used to decrypt data
     * @return String
     * @throws KeyDoesNotExistsException if key does not exists
     */
    fun decrypt(encryptedData: EncryptedData, key: SecretKey): String
}

internal class EncryptionManagerImpl : EncryptionManager {

    override fun encrypt(value: String, key: SecretKey): EncryptedData {
        val cipher = Cipher.getInstance(KEY_CIPHER_TRANSFORMATION).also {
            it.init(Cipher.ENCRYPT_MODE, key)
        }

        val valueByteArray = value.toByteArray(Charsets.UTF_8)

        return EncryptedData(cipher.iv, cipher.doFinal(valueByteArray))
    }

    override fun decrypt(encryptedData: EncryptedData, key: SecretKey): String {
        val cipher = Cipher.getInstance(KEY_CIPHER_TRANSFORMATION).also {
            it.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(encryptedData.iv))
        }

        return cipher.doFinal(encryptedData.data).toString(Charsets.UTF_8).trim()
    }
}
