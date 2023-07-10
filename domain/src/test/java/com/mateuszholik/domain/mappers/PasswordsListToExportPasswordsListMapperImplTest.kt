package com.mateuszholik.domain.mappers

import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.domain.models.ExportedPassword
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class PasswordsListToExportPasswordsListMapperImplTest {

    private val passwordToExportedPasswordMapper = mockk<PasswordToExportedPasswordMapper>()

    private val passwordsListToExportedPasswordsListMapper = PasswordsListToExportPasswordsListMapperImpl(
        passwordToExportedPasswordMapper = passwordToExportedPasswordMapper
    )

    @Test
    fun `List of passwords objects is properly mapped to list of ExportedPassword objects`() {
        every {
            passwordToExportedPasswordMapper.map(PASSWORDInfo)
        } returns EXPORTED_PASSWORD

        val result = passwordsListToExportedPasswordsListMapper.map(listOf(PASSWORDInfo))

        assertThat(result).isEqualTo(listOf(EXPORTED_PASSWORD))
    }

    private companion object {
        const val PASSWORD_STRING = "password"
        const val PLATFORM_NAME = "platformName"
        val PASSWORDInfo = PasswordInfo(
            id = 1L,
            password = PASSWORD_STRING,
            platformName = PLATFORM_NAME,
            expiringDate = LocalDateTime.of(2023, 7, 1, 12, 0, 0)
        )
        val EXPORTED_PASSWORD = ExportedPassword(
            platformName = PLATFORM_NAME,
            password = PASSWORD_STRING
        )
    }
}
