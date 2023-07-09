package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.OldPasswordEntity
import com.mateuszholik.data.repositories.models.Password

internal interface PasswordDBToPasswordMapper : Mapper<OldPasswordEntity, Password>

internal class PasswordDBToPasswordMapperImpl(
    private val encryptionManager: KeyBaseEncryptionManager
) : PasswordDBToPasswordMapper {

    override fun map(param: OldPasswordEntity): Password =
        Password(
            id = param.id,
            platformName = encryptionManager.decrypt(
                EncryptedData(
                    iv = param.platformIV,
                    data = param.platformName
                )
            ),
            password = encryptionManager.decrypt(
                EncryptedData(
                    iv = param.passwordIV,
                    data = param.password
                )
            ),
            expiringDate = param.expirationDate
        )
}
