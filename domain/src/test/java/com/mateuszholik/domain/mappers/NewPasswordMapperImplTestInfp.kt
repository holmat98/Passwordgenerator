package com.mateuszholik.domain.mappers

import com.mateuszholik.domain.models.NewPassword
import org.assertj.core.api.Assertions.assertThat
import com.mateuszholik.data.repositories.models.NewPassword as DataNewPassword
import org.junit.jupiter.api.Test

internal class NewPasswordMapperImplTestInfp {

    private val newPasswordMapper = NewPasswordMapperImpl()

    @Test
    fun `Mapper correctly mapped domain new password to data new password model`() {
        val result = newPasswordMapper.map(NEW_PASSWORD)

        assertThat(result).isEqualTo(DATA_NEW_PASSWORD)
    }

    private companion object {
        const val PASSWORD = "password"
        const val PLATFORM = "platform"
        val NEW_PASSWORD = NewPassword(
            password = PASSWORD,
            platformName = PLATFORM
        )
        val DATA_NEW_PASSWORD = DataNewPassword(
            password = PASSWORD,
            platformName = PLATFORM
        )
    }
}
