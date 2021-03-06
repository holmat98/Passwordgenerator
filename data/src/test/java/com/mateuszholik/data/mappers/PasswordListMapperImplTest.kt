package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.repositories.models.Password
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDateTime

@RunWith(JUnit4::class)
class PasswordListMapperImplTest {

    private val passwordMapper = mockk<PasswordMapper> {
        every { map(PASSWORD_DB_1) } returns MAPPED_PASSWORD_1
        every { map(PASSWORD_DB_2) } returns MAPPED_PASSWORD_2
    }

    private val passwordListMapper = PasswordListMapperImpl(passwordMapper)

    @Test
    fun `PasswordListMapper maps correctly list of PasswordDB objects to list of Password objects`() {
        val result = passwordListMapper.map(listOf(PASSWORD_DB_1, PASSWORD_DB_2))

        assertThat(result).isEqualTo(listOf(MAPPED_PASSWORD_1, MAPPED_PASSWORD_2))
    }

    private companion object {
        const val PLATFORM_NAME = "platform"
        const val PASSWORD = "password1234"
        const val PLATFORM_NAME_2 = "platform2"
        const val PASSWORD_2 = "password12345"
        val EXPIRING_DATE: LocalDateTime = LocalDateTime.of(2022, 6, 11, 12, 0, 0)
        val PASSWORD_DB_1 = PasswordDB(
            id = 1,
            platformName = ByteArray(10),
            platformIV = ByteArray(11),
            password = ByteArray(12),
            passwordIV = ByteArray(13),
            expiringDate = EXPIRING_DATE
        )
        val PASSWORD_DB_2 = PasswordDB(
            id = 2,
            platformName = ByteArray(10),
            platformIV = ByteArray(11),
            password = ByteArray(12),
            passwordIV = ByteArray(13),
            expiringDate = EXPIRING_DATE
        )
        val MAPPED_PASSWORD_1 = Password(
            id = 1,
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            expiringDate = EXPIRING_DATE
        )
        val MAPPED_PASSWORD_2 = Password(
            id = 2,
            platformName = PLATFORM_NAME_2,
            password = PASSWORD_2,
            expiringDate = EXPIRING_DATE
        )
    }
}