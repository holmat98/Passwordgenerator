package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.repositories.models.Password

internal interface PasswordDBToPasswordMapper : Mapper<PasswordDB, Password>

internal class PasswordDBToPasswordMapperImpl(
    private val encryptionManager: KeyBaseEncryptionManager
) : PasswordDBToPasswordMapper {

    override fun map(param: PasswordDB): Password =
        Password(
            platformName = encryptionManager.decrypt(
                EncryptedData(
                    iv = param.platformNameIv,
                    data = param.platformName
                )
            ),
            password = encryptionManager.decrypt(
                EncryptedData(
                    iv = param.passwordIv,
                    data = param.password
                )
            )
        )
}
