package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.data.db.models.entities.NamesEntity
import com.mateuszholik.data.repositories.models.NewPassword

internal interface NewPasswordToNamesEntityMapper : Mapper<NewPassword, NamesEntity>

internal class NewPasswordToNamesEntityMapperImpl(
    private val encryptionManager: KeyBaseEncryptionManager
) : NewPasswordToNamesEntityMapper {

    override fun map(param: NewPassword): NamesEntity {
        val encryptedPlatformName = encryptionManager.encrypt(param.platformName)
        val encryptedWebsite = param.website?.let { encryptionManager.encrypt(it) }

        return NamesEntity(
            id = 0,
            platformName = encryptedPlatformName.data,
            platformNameIv = encryptedPlatformName.iv,
            website = encryptedWebsite?.data,
            websiteIv = encryptedWebsite?.iv
        )
    }
}
