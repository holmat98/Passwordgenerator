package com.mateuszholik.domain.mappers

import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.models.ExportedPassword
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class PasswordToExportedPasswordMapperImplTest {

    private val passwordToExportedPasswordMapper = PasswordToExportedPasswordMapperImpl()

    @Test
    fun `Password object is properly mapped to ExportedPassword object`() {
        val result = passwordToExportedPasswordMapper.map(PASSWORD)

        assertThat(result).isEqualTo(EXPORTED_PASSWORD)
    }

    private companion object {
        const val PASSWORD_STRING = "password"
        const val PLATFORM_NAME = "platformName"
        val PASSWORD = Password(
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