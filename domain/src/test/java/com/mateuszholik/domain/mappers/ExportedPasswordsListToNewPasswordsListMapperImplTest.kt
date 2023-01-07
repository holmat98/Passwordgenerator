package com.mateuszholik.domain.mappers

import com.mateuszholik.data.repositories.models.NewPassword
import com.mateuszholik.domain.models.ExportedPassword
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ExportedPasswordsListToNewPasswordsListMapperImplTest {

    private val exportedPasswordToNewPasswordMapper = mockk<ExportedPasswordToNewPasswordMapper>()

    private val exportedPasswordsListToNewPasswordsListMapper =
        ExportedPasswordsListToNewPasswordsListMapperImpl(
            exportedPasswordToNewPasswordMapper = exportedPasswordToNewPasswordMapper
        )

    @Test
    fun `List of ExportedPassword objects is properly mapped to list of NewPassword objects`() {
        every {
            exportedPasswordToNewPasswordMapper.map(EXPORTED_PASSWORD)
        } returns NEW_PASSWORD

        val result = exportedPasswordsListToNewPasswordsListMapper.map(listOf(EXPORTED_PASSWORD))

        assertThat(result).isEqualTo(listOf(NEW_PASSWORD))
    }

    private companion object {
        const val PASSWORD = "password"
        const val PLATFORM_NAME = "platformName"
        val NEW_PASSWORD = NewPassword(
            password = PASSWORD,
            platformName = PLATFORM_NAME
        )
        val EXPORTED_PASSWORD = ExportedPassword(
            platformName = PLATFORM_NAME,
            password = PASSWORD
        )
    }
}