package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.views.PasswordInfoView
import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.data.repositories.models.PasswordValidity
import java.time.LocalDateTime

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

    private fun LocalDateTime.toPasswordValidity(): PasswordValidity {
        val now = LocalDateTime.now()

        return when {
            now.isBefore(this.minusDays(EXPIRING_PASSWORD_TIME_IN_DAYS)) ->
                PasswordValidity.VALID
            now.isAfter(this) ->
                PasswordValidity.EXPIRED
            else -> PasswordValidity.EXPIRING
        }
    }

    private companion object {
        const val EXPIRING_PASSWORD_TIME_IN_DAYS = 7L
    }
}
