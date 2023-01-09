package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.repositories.models.NewPassword
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class NewPasswordsListToPasswordDBListMapperImplTest {

    private val newPasswordToPasswordDBMapper = mockk<NewPasswordToPasswordDBMapper> {
        every { map(NEW_PASSWORD) } returns PASSWORD_DB
        every { map(NEW_PASSWORD_2) } returns PASSWORD_DB_2
    }

    private val newPasswordsListToPasswordDBListMapper = NewPasswordsListToPasswordDBListMapperImpl(
        newPasswordToPasswordDBMapper = newPasswordToPasswordDBMapper
    )

    @Test
    fun `NewPasswordListMapper maps correctly list of NewPassword objects to list of PasswordDB objects`() {
        val result = newPasswordsListToPasswordDBListMapper.map(listOf(NEW_PASSWORD, NEW_PASSWORD_2))

        assertThat(result).isEqualTo(listOf(PASSWORD_DB, PASSWORD_DB_2))
    }

    private companion object {
        const val PLATFORM_NAME = "platform"
        const val PASSWORD = "password1234"
        const val PLATFORM_NAME_2 = "platform2"
        const val PASSWORD_2 = "password12345"
        val EXPIRING_DATE: LocalDateTime = LocalDateTime.of(2022, 6, 11, 12, 0, 0)
        val PASSWORD_DB = PasswordDB(
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
        val NEW_PASSWORD = NewPassword(
            platformName = PLATFORM_NAME,
            password = PASSWORD,
        )
        val NEW_PASSWORD_2 = NewPassword(
            platformName = PLATFORM_NAME_2,
            password = PASSWORD_2,
        )
    }
}