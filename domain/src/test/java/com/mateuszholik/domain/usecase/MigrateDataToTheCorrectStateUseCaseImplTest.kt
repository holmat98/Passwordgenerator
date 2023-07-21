package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.MigrationRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Completable
import org.junit.jupiter.api.Test

internal class MigrateDataToTheCorrectStateUseCaseImplTest {

    private val migrationRepository = mockk<MigrationRepository>()
    private val getPasswordScoreUseCase = mockk<GetPasswordScoreUseCase>()

    private val migrateDataToCorrectToSaveUseCase = MigrateDataToTheCorrectStateUseCaseImpl(
        migrationRepository = migrationRepository,
        getPasswordScoreUseCase = getPasswordScoreUseCase
    )

    @Test
    fun `When migrationRepository migrate returns Completable Complete then use case also returns Completable Complete`() {
        every {
            migrationRepository.migrate(any())
        } returns Completable.complete()

        migrateDataToCorrectToSaveUseCase()
            .test()
            .assertComplete()
    }
}
