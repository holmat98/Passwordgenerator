package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.views.PasswordInfoView
import com.mateuszholik.data.extensions.toPasswordValidity
import com.mateuszholik.data.repositories.models.PasswordInfo

internal interface PasswordInfoViewToPasswordInfoMapper : Mapper<PasswordInfoView, PasswordInfo>

internal class PasswordInfoViewToPasswordInfoMapperImpl(
    private val encryptionManager: KeyBaseEncryptionManager
) : PasswordInfoViewToPasswordInfoMapper {

    override fun map(param: PasswordInfoView): PasswordInfo =
        PasswordInfo(
            id = param.id,
            platformName = encryptionManager.decrypt(
                EncryptedData(
                    iv = param.platformNameIv,
                    data = param.platformName
                )
            ),
            passwordScore = param.passwordScore,
            passwordValidity = param.expirationDate.toPasswordValidity()
        )
}
