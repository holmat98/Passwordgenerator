package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.OldPasswordEntity
import com.mateuszholik.data.repositories.models.Password
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class PasswordDBToPasswordMapperImplTest {

    private val encryptionManager = mockk<KeyBaseEncryptionManager> {
        every {
            decrypt(
                EncryptedData(
                    iv = PASSWORD_DB.passwordIV,
                    data = PASSWORD_DB.password
                )
            )
        } returns PASSWORD

        every {
            decrypt(
                EncryptedData(
                    iv = PASSWORD_DB.platformIV,
                    data = PASSWORD_DB.platformName
                )
            )
        } returns PLATFORM_NAME
    }

    private val passwordMapper = PasswordDBToPasswordMapperImpl(encryptionManager)

    @Test
    fun `PasswordMapper maps correctly PasswordDB object to Password object`() {
        val result = passwordMapper.map(PASSWORD_DB)

        assertThat(result).isEqualTo(EXPECTED)
    }

    private companion object {
        const val PLATFORM_NAME = "platform"
        const val PASSWORD = "password1234"
        val EXPIRING_DATE: LocalDateTime = LocalDateTime.of(2022, 6, 11, 12, 0, 0)
        val PASSWORD_DB = OldPasswordEntity(
            id = 1,
            platformName = ByteArray(10),
            platformIV = ByteArray(11),
            password = ByteArray(12),
            passwordIV = ByteArray(13),
            expirationDate = EXPIRING_DATE
        )
        val EXPECTED = Password(
            id = 1,
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            expiringDate = EXPIRING_DATE
        )
    }
}
