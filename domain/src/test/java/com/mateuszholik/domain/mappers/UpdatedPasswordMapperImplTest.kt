package com.mateuszholik.domain.mappers

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import com.mateuszholik.data.repositories.models.UpdatedPassword as DataUpdatedPassword

internal class UpdatedPasswordMapperImplTest {

    private val updatedPasswordMapper = UpdatedPasswordMapperImpl()

    @Test
    fun `Mapper correctly mapped domain updated password to data updated password model`() {
        val result = updatedPasswordMapper.map(UPDATED_PASSWORD)

        Assertions.assertThat(result).isEqualTo(DATA_UPDATED_PASSWORD)
    }

    private companion object {
        const val ID = 1L
        const val PASSWORD = "password"
        const val PLATFORM = "platform"
        const val IS_EXPIRING = false
        const val PASSWORD_SCORE = 50
        val UPDATED_PASSWORD = UpdatedPasswordMapper.Param(
            id = ID,
            password = PASSWORD,
            platformName = PLATFORM,
            website = null,
            isExpiring = IS_EXPIRING,
            passwordScore = PASSWORD_SCORE
        )
        val DATA_UPDATED_PASSWORD = DataUpdatedPassword(
            id = ID,
            password = PASSWORD,
            platformName = PLATFORM,
            website = null,
            isExpiring = IS_EXPIRING,
            passwordScore = PASSWORD_SCORE
        )
    }
}
