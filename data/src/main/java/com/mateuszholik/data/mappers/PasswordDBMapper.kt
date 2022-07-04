package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.EncryptionManager
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.repositories.models.Password

internal interface PasswordDBMapper: Mapper<Password, PasswordDB>

internal class PasswordDBMapperImpl(
    private val encryptionManager: EncryptionManager
): PasswordDBMapper {

    override fun map(param: Password): PasswordDB {
        val encryptedPlatformName = encryptionManager.encrypt(param.platformName)
        val encryptedPassword = encryptionManager.encrypt(param.password)

        return PasswordDB(
            id = param.id,
            platformName = encryptedPlatformName.data,
            platformIV = encryptedPlatformName.iv,
            password = encryptedPassword.data,
            passwordIV = encryptedPassword.iv,
            expiringDate = param.expiringDate
        )
    }
}