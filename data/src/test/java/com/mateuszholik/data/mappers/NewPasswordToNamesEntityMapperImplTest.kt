package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.entities.NamesEntity
import com.mateuszholik.data.repositories.models.NewPassword
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class NewPasswordToNamesEntityMapperImplTest {

    private val encryptionManager = mockk<KeyBaseEncryptionManager> {
        every { encrypt(any()) } returns EncryptedData(
            iv = ENCRYPTION_RESULT,
            data = ENCRYPTION_RESULT,
        )
    }

    private val newPasswordToNamesEntityMapper = NewPasswordToNamesEntityMapperImpl(
        encryptionManager = encryptionManager
    )

    @Test
    fun `NewPassword is correctly mapped to the NamesEntity object`() {
        val result = newPasswordToNamesEntityMapper.map(NEW_PASSWORD)

        assertThat(result).isEqualTo(NAMES_ENTITY)
    }

    @Test
    fun `When website and packageName are null then NewPassword is correctly mapped to the NamesEntity object`() {
        val result = newPasswordToNamesEntityMapper.map(NEW_PASSWORD_WITHOUT_WEBSITE)

        assertThat(result).isEqualTo(NAMES_ENTITY_WITHOUT_WEBSITE)
    }

    private companion object {
        val ENCRYPTION_RESULT = ByteArray(5) { it.toByte() }
        const val PLATFORM_NAME = "platform"
        const val PASSWORD = "password"
        const val WEBSITE = "website"
        const val PASSWORD_SCORE = 90
        const val PACKAGE_NAME = "com.example.package"
        const val IS_EXPIRING = true
        val NEW_PASSWORD = NewPassword(
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            website = WEBSITE,
            passwordScore = PASSWORD_SCORE,
            isExpiring = IS_EXPIRING,
            packageName = PACKAGE_NAME
        )
        val NEW_PASSWORD_WITHOUT_WEBSITE = NewPassword(
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            website = null,
            passwordScore = PASSWORD_SCORE,
            isExpiring = IS_EXPIRING,
            packageName = null
        )
        val NAMES_ENTITY = NamesEntity(
            id = 0,
            platformName = ENCRYPTION_RESULT,
            platformNameIv = ENCRYPTION_RESULT,
            website = ENCRYPTION_RESULT,
            websiteIv = ENCRYPTION_RESULT,
            packageName = ENCRYPTION_RESULT,
            packageNameIv = ENCRYPTION_RESULT,
        )
        val NAMES_ENTITY_WITHOUT_WEBSITE = NamesEntity(
            id = 0,
            platformName = ENCRYPTION_RESULT,
            platformNameIv = ENCRYPTION_RESULT,
            website = null,
            websiteIv = null,
            packageName = null,
            packageNameIv = null,
        )
    }
}
