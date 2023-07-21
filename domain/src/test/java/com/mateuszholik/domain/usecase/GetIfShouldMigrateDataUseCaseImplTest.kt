package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.SharedPrefKeys.WAS_MIGRATION_COMPLETED
import com.mateuszholik.data.managers.io.SharedPrefManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class GetIfShouldMigrateDataUseCaseImplTest {

    private val sharedPrefManager = mockk<SharedPrefManager> {
        every { readBoolean(WAS_MIGRATION_COMPLETED) } returns true
    }

    private val getIfShouldMigrateDataUseCase = GetIfShouldMigrateDataUseCaseImpl(
        sharedPrefManager = sharedPrefManager
    )

    @Test
    fun `GetIfShouldMigrateDataUseCaseImpl correctly returns data received from sharedPrefManager`() {
        getIfShouldMigrateDataUseCase()
            .test()
            .assertComplete()
            .assertValue(true)

        verify(exactly = 1) { sharedPrefManager.readBoolean(WAS_MIGRATION_COMPLETED) }
    }

}
