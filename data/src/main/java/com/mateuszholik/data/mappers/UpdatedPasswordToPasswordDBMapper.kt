package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.data.db.models.PasswordEntity
import com.mateuszholik.data.managers.io.SharedPrefKeys.PASSWORD_VALIDITY
import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.data.repositories.models.UpdatedPassword
import java.time.LocalDateTime

internal interface UpdatedPasswordToPasswordDBMapper : Mapper<UpdatedPassword, PasswordEntity>

internal class UpdatedPasswordToPasswordDBMapperImpl(
    private val encryptionManager: KeyBaseEncryptionManager,
    private val sharedPrefManager: SharedPrefManager
) : UpdatedPasswordToPasswordDBMapper {

    override fun map(param: UpdatedPassword): PasswordEntity {
        val encryptedPlatformName = encryptionManager.encrypt(param.platformName)
        val encryptedPassword = encryptionManager.encrypt(param.password)
        val expiringDate = sharedPrefManager.readLong(PASSWORD_VALIDITY, DEFAULT_PASSWORD_VALIDITY)

        return PasswordEntity(
            id = param.id,
            nameId = 0,
            platformName = encryptedPlatformName.data,
            platformIV = encryptedPlatformName.iv,
            password = encryptedPassword.data,
            passwordIV = encryptedPassword.iv,
            passwordScore = 0,
            expirationDate = LocalDateTime.now().plusDays(expiringDate)
        )
    }

    private companion object {
        const val DEFAULT_PASSWORD_VALIDITY = 30L
    }
}
