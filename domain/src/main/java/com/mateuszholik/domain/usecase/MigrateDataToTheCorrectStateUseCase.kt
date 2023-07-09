package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.MigrationRepository
import com.mateuszholik.domain.usecase.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable

interface MigrateDataToTheCorrectStateUseCase : CompletableUseCase

internal class MigrateDataToTheCorrectStateUseCaseImpl(
    private val migrationRepository: MigrationRepository,
    private val getPasswordScoreUseCase: GetPasswordScoreUseCase,
) : MigrateDataToTheCorrectStateUseCase {

    override fun invoke(): Completable =
        migrationRepository.migrate {
            getPasswordScoreUseCase(it)
        }

}
