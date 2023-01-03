package com.mateuszholik.cryptography

import com.mateuszholik.cryptography.models.EncryptedData

interface KeyBaseEncryptionManager {

    /**
     * Encrypt data
     *
     * @param value String that will be encrypted
     * @return [EncryptedData][com.mateuszholik.cryptography.models.EncryptedData]
     */
    fun encrypt(value: String): EncryptedData

    /**
     * Decrypt data
     *
     * @param encryptedData [EncryptedData][com.mateuszholik.cryptography.models.EncryptedData]
     * @return String
     * @throws KeyDoesNotExistsException if key does not exists
     */
    fun decrypt(encryptedData: EncryptedData): String
}

internal class KeyBaseEncryptionManagerImpl(
    private val cryptographyKeyManager: CryptographyKeyManager,
    private val encryptionManager: EncryptionManager
) : KeyBaseEncryptionManager {

    override fun encrypt(value: String): EncryptedData {
        if (!cryptographyKeyManager.isKeyCreated()) {
            cryptographyKeyManager.createKey()
        }

        return encryptionManager.encrypt(value, cryptographyKeyManager.getKey())
    }

    override fun decrypt(encryptedData: EncryptedData): String {
        if (!cryptographyKeyManager.isKeyCreated()) {
            throw KeyDoesNotExistsException()
        }

        return encryptionManager.decrypt(encryptedData, cryptographyKeyManager.getKey())
    }
}