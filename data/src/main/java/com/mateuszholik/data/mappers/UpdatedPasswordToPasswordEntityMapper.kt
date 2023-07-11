package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.data.db.models.entities.PasswordEntity
import com.mateuszholik.data.managers.io.SharedPrefKeys.PASSWORD_VALIDITY
import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordEntityMapper.Param
import com.mateuszholik.data.repositories.models.UpdatedPassword
import java.time.LocalDateTime

internal interface UpdatedPasswordToPasswordEntityMapper : Mapper<Param, PasswordEntity> {

    data class Param(
        val updatedPassword: UpdatedPassword,
        val nameId: Long,
    )
}

internal class UpdatedPasswordToPasswordEntityMapperImpl(
    private val encryptionManager: KeyBaseEncryptionManager,
    private val sharedPrefManager: SharedPrefManager,
) : UpdatedPasswordToPasswordEntityMapper {

    override fun map(param: Param): PasswordEntity {
        val encryptedPassword = encryptionManager.encrypt(param.updatedPassword.password)
        val expiringDate = sharedPrefManager.readLong(PASSWORD_VALIDITY, DEFAULT_PASSWORD_VALIDITY)

        return PasswordEntity(
            id = param.updatedPassword.id,
            nameId = param.nameId,
            password = encryptedPassword.data,
            passwordIV = encryptedPassword.iv,
            expirationDate = LocalDateTime.now().plusDays(expiringDate)
                .takeIf { param.updatedPassword.isExpiring },
            passwordScore = param.updatedPassword.passwordScore
        )
    }

    private companion object {
        const val DEFAULT_PASSWORD_VALIDITY = 30L
    }
}
