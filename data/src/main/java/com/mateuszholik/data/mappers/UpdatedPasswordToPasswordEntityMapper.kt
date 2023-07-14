package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.data.db.models.entities.PasswordEntity
import com.mateuszholik.data.managers.io.SharedPrefKeys.PASSWORD_VALIDITY
import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordEntityMapper.Param
import java.time.LocalDateTime

internal interface UpdatedPasswordToPasswordEntityMapper : Mapper<Param, PasswordEntity> {

    data class Param(
        val id: Long,
        val password: String,
        val passwordScore: Int,
        val isExpiring: Boolean,
        val nameId: Long,
    )
}

internal class UpdatedPasswordToPasswordEntityMapperImpl(
    private val encryptionManager: KeyBaseEncryptionManager,
    private val sharedPrefManager: SharedPrefManager,
) : UpdatedPasswordToPasswordEntityMapper {

    override fun map(param: Param): PasswordEntity {
        val encryptedPassword = encryptionManager.encrypt(param.password)
        val expiringDate = sharedPrefManager.readLong(PASSWORD_VALIDITY, DEFAULT_PASSWORD_VALIDITY)

        return PasswordEntity(
            id = param.id,
            nameId = param.nameId,
            password = encryptedPassword.data,
            passwordIV = encryptedPassword.iv,
            expirationDate = LocalDateTime.now().plusDays(expiringDate).takeIf { param.isExpiring },
            passwordScore = param.passwordScore
        )
    }

    private companion object {
        const val DEFAULT_PASSWORD_VALIDITY = 30L
    }
}
