package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.EncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.repositories.models.Password

internal interface PasswordMapper : Mapper<PasswordDB, Password>

internal class PasswordMapperImpl(
    private val encryptionManager: EncryptionManager
) : PasswordMapper {

    override fun map(param: PasswordDB): Password =
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
            expiringDate = param.expiringDate
        )
}