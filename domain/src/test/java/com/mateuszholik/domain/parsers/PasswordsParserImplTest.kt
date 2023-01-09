package com.mateuszholik.domain.parsers

import com.mateuszholik.domain.models.ExportedPassword
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PasswordsParserImplTest {

    private val passwordsParser = PasswordsParserImpl()

    @Test
    fun `List of passwords is properly converted to string`() {
        val result = passwordsParser.parseToString(listOf(EXPORTED_PASSWORD, EXPORTED_PASSWORD_2))

        assertThat(result).isEqualTo(PARSED_PASSWORDS)
    }

    @Test
    fun `String is properly converted to list of passwords`() {
        val result = passwordsParser.parseFromString(PARSED_PASSWORDS)

        assertThat(result).isEqualTo(listOf(EXPORTED_PASSWORD, EXPORTED_PASSWORD_2))
    }

    private companion object {
        val EXPORTED_PASSWORD = ExportedPassword(
            platformName = "platformName",
            password = "password"
        )
        val EXPORTED_PASSWORD_2 = ExportedPassword(
            platformName = "platformName2",
            password = "password2"
        )
        const val PARSED_PASSWORDS = "platformName:password\nplatformName2:password2"
    }
}