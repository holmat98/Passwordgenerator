package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.SharedPrefKeys
import com.mateuszholik.data.managers.io.SharedPrefKeys.WAS_MIGRATION_COMPLETED
import com.mateuszholik.data.managers.io.SharedPrefManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class SaveMigrationStateUseCaseImplTest {

    private val sharedPrefManager = mockk<SharedPrefManager> {
        every { write(WAS_MIGRATION_COMPLETED, true) } returns Unit
    }

    private val saveMigrationStateUseCase = SaveMigrationStateUseCaseImpl(
        sharedPrefManager = sharedPrefManager
    )

    @Test
    fun `GetIfShouldMigrateDataUseCaseImpl returns Completable Complete after data was written to sharedPrefManager`() {
        saveMigrationStateUseCase()
            .test()
            .assertComplete()

        verify(exactly = 1) { sharedPrefManager.write(WAS_MIGRATION_COMPLETED, true) }
    }
}
