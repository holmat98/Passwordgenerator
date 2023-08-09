package com.mateuszholik.domain.mappers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import com.mateuszholik.data.repositories.models.NewPassword as DataNewPassword

internal class NewPasswordMapperImplTest {

    private val newPasswordMapper = NewPasswordMapperImpl()

    @Test
    fun `Mapper correctly mapped domain new password to data new password model`() {
        val result = newPasswordMapper.map(NEW_PASSWORD)

        assertThat(result).isEqualTo(DATA_NEW_PASSWORD)
    }

    private companion object {
        const val PASSWORD = "password"
        const val PLATFORM = "platform"
        const val IS_EXPIRING = false
        const val PASSWORD_SCORE = 50
        const val PACKAGE_NAME = "com.example.package"
        val NEW_PASSWORD = NewPasswordMapper.Param(
            password = PASSWORD,
            platformName = PLATFORM,
            website = null,
            isExpiring = IS_EXPIRING,
            passwordScore = PASSWORD_SCORE,
            packageName = PACKAGE_NAME
        )
        val DATA_NEW_PASSWORD = DataNewPassword(
            password = PASSWORD,
            platformName = PLATFORM,
            website = null,
            isExpiring = IS_EXPIRING,
            passwordScore = PASSWORD_SCORE,
            packageName = PACKAGE_NAME
        )
    }
}
