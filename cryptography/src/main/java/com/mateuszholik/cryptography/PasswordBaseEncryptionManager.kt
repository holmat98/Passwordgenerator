package com.mateuszholik.cryptography

import com.mateuszholik.cryptography.factories.SecretKeySpecFactory
import com.mateuszholik.cryptography.models.EncryptedData

interface PasswordBaseEncryptionManager {

    /**
     * Encrypt data
     *
     * @param password String that will be used to create encryption key
     * @param dataToEncrypt String that will be encrypted
     * @return [EncryptedData][com.mateuszholik.cryptography.models.EncryptedData]
     */
    fun encrypt(password: String, dataToEncrypt: String): EncryptedData

    /**
     * Decrypt data
     *
     * @param password String that will be used to create encryption key
     * @param encryptedData [EncryptedData][com.mateuszholik.cryptography.models.EncryptedData]
     * @return [EncryptedData][com.mateuszholik.cryptography.models.EncryptedData]
     */
    fun decrypt(password: String, encryptedData: EncryptedData): String
}

internal class PasswordBaseEncryptionManagerImpl(
    private val secretKeySpecFactory: SecretKeySpecFactory,
    private val encryptionManager: EncryptionManager
) : PasswordBaseEncryptionManager {

    override fun encrypt(password: String, dataToEncrypt: String): EncryptedData {
        val key = secretKeySpecFactory.create(password)

        return encryptionManager.encrypt(dataToEncrypt, key)
    }

    override fun decrypt(password: String, encryptedData: EncryptedData): String {
        val key = secretKeySpecFactory.create(password)

        return encryptionManager.decrypt(encryptedData, key)
    }
}