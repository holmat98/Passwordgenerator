package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.views.AutofillPasswordDetailsView
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails

internal interface AutofillPasswordDetailsViewMapper :
    Mapper<AutofillPasswordDetailsView, AutofillPasswordDetails>

internal class AutofillPasswordDetailsViewMapperImpl(
    private val encryptionManager: KeyBaseEncryptionManager,
) : AutofillPasswordDetailsViewMapper {

    override fun map(param: AutofillPasswordDetailsView): AutofillPasswordDetails {
        val decryptedPlatformName = encryptionManager.decrypt(
            EncryptedData(
                iv = param.platformNameIv,
                data = param.platformName
            )
        )
        val decryptedPassword = encryptionManager.decrypt(
            EncryptedData(
                iv = param.passwordIv,
                data = param.password
            )
        )
        val decryptedPackageName = if (param.packageName != null && param.packageNameIv != null) {
            encryptionManager.decrypt(
                EncryptedData(
                    iv = param.packageNameIv,
                    data = param.packageName
                )
            )
        } else {
            null
        }

        return AutofillPasswordDetails(
            id = param.id,
            platformName = decryptedPlatformName,
            password = decryptedPassword,
            packageName = decryptedPackageName
        )
    }

}
