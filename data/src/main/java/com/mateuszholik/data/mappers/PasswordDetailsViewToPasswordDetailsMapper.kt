package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.views.PasswordDetailsView
import com.mateuszholik.data.extensions.toPasswordValidity
import com.mateuszholik.data.repositories.models.PasswordDetails

internal interface PasswordDetailsViewToPasswordDetailsMapper :
    Mapper<PasswordDetailsView, PasswordDetails>

internal class PasswordDetailsViewToPasswordDetailsMapperImpl(
    private val encryptionManager: KeyBaseEncryptionManager,
) : PasswordDetailsViewToPasswordDetailsMapper {

    override fun map(param: PasswordDetailsView): PasswordDetails =
        PasswordDetails(
            id = param.id,
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
            ),
            website = if (param.website != null && param.websiteIv != null) {
                encryptionManager.decrypt(
                    EncryptedData(
                        iv = param.websiteIv,
                        data = param.website
                    )
                )
            } else {
                null
            },
            passwordScore = param.passwordScore,
            passwordValidity = param.expirationDate.toPasswordValidity()
        )
}
