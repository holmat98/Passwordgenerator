package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.repositories.models.Password
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PasswordsDBListToPasswordsListMapperImplTest {

    private val passwordDBToPasswordMapper = mockk<PasswordDBToPasswordMapper> {
        every { map(PASSWORD_DB) } returns PASSWORD_RESULT
        every { map(PASSWORD_DB_2) } returns PASSWORD_RESULT_2
    }

    private val passwordsDBListToPasswordsListMapper = PasswordsDBListToPasswordsListMapperImpl(
        passwordDBToPasswordMapper = passwordDBToPasswordMapper
    )

    @Test
    fun `List of PasswordDB objects is correctly mapped to the list of Password objects`() {
        val result = passwordsDBListToPasswordsListMapper.map(listOf(PASSWORD_DB, PASSWORD_DB_2))

        assertThat(result).isEqualTo(listOf(PASSWORD_RESULT, PASSWORD_RESULT_2))
    }

    private companion object {
        const val PASSWORD = "password"
        const val PASSWORD_2 = "password2"
        const val PLATFORM_NAME = "platformName"
        const val PLATFORM_NAME_2 = "platformName2"
        val IV = ByteArray(5) { it.toByte() }
        val PASSWORD_DB = PasswordDB(
            platformName = PLATFORM_NAME.toByteArray(),
            platformNameIv = IV,
            password = PASSWORD.toByteArray(),
            passwordIv = IV
        )
        val PASSWORD_DB_2 = PasswordDB(
            platformName = PLATFORM_NAME_2.toByteArray(),
            platformNameIv = IV,
            password = PASSWORD_2.toByteArray(),
            passwordIv = IV
        )
        val PASSWORD_RESULT = Password(PLATFORM_NAME, PASSWORD)
        val PASSWORD_RESULT_2 = Password(PLATFORM_NAME_2, PASSWORD_2)
    }
}
