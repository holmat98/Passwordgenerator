package com.mateuszholik.domain.mappers

import com.mateuszholik.data.repositories.models.NewPassword
import com.mateuszholik.domain.models.ExportedPassword
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ExportedPasswordToNewPasswordMapperImplTestInfp {

    private val exportedPasswordToNewPassword = ExportedPasswordToNewPasswordMapperImpl()

    @Test
    fun `ExportedPassword object is properly mapped to NewPassword object`() {
        val result = exportedPasswordToNewPassword.map(EXPORTED_PASSWORD)

        assertThat(result).isEqualTo(NEW_PASSWORD)
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
