package com.mateuszholik.domain.mappers

import com.mateuszholik.domain.models.UpdatedPassword
import com.mateuszholik.data.repositories.models.UpdatedPassword as DataUpdatedPassword
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class UpdatedPasswordMapperImplTestInfp {

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
        val UPDATED_PASSWORD = UpdatedPassword(
            id = ID,
            password = PASSWORD,
            platformName = PLATFORM
        )
        val DATA_UPDATED_PASSWORD = DataUpdatedPassword(
            id = ID,
            password = PASSWORD,
            platformName = PLATFORM
        )
    }
}
