package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.EncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.repositories.models.Password
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDateTime
import org.assertj.core.api.Assertions.assertThat


class PasswordMapperImplTest {

    private val encryptionManager = mockk<EncryptionManager> {
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

    private val passwordMapper = PasswordMapperImpl(encryptionManager)

    @Test
    fun `PasswordMapper maps correctly PasswordDB object to Password object`() {
        val result = passwordMapper.map(PASSWORD_DB)

        assertThat(result).isEqualTo(EXPECTED)
    }

    private companion object {
        const val PLATFORM_NAME = "platform"
        const val PASSWORD = "password1234"
        val EXPIRING_DATE: LocalDateTime = LocalDateTime.of(2022, 6, 11, 12, 0, 0)
        val PASSWORD_DB = PasswordDB(
            id = 1,
            platformName = ByteArray(10),
            platformIV = ByteArray(11),
            password = ByteArray(12),
            passwordIV = ByteArray(13),
            expiringDate = EXPIRING_DATE
        )
        val EXPECTED = Password(
            id = 1,
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            expiringDate = EXPIRING_DATE
        )
    }
}