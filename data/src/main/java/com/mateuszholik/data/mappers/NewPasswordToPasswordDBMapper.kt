package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.EncryptionManager
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.managers.io.SharedPrefKeys.PASSWORD_VALIDITY
import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.data.repositories.models.NewPassword
import java.time.LocalDateTime

internal interface NewPasswordToPasswordDBMapper : Mapper<NewPassword, PasswordDB>

internal class NewPasswordToPasswordDBMapperImpl(
    private val encryptionManager: EncryptionManager,
    private val sharedPrefManager: SharedPrefManager
) : NewPasswordToPasswordDBMapper {

    override fun map(param: NewPassword): PasswordDB {
        val encryptedPlatformName = encryptionManager.encrypt(param.platformName)
        val encryptedPassword = encryptionManager.encrypt(param.password)
        val expiringDate = sharedPrefManager.readLong(PASSWORD_VALIDITY, DEFAULT_PASSWORD_VALIDITY)

        return PasswordDB(
            id = DEFAULT_ID,
            platformName = encryptedPlatformName.data,
            platformIV = encryptedPlatformName.iv,
            password = encryptedPassword.data,
            passwordIV = encryptedPassword.iv,
            expiringDate = LocalDateTime.now().plusDays(expiringDate)
        )
    }

    private companion object {
        const val DEFAULT_ID = 0L
        const val DEFAULT_PASSWORD_VALIDITY = 30L
    }
}