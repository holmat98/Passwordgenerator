package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.SharedPrefKeys.WAS_MIGRATION_COMPLETED
import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.domain.usecase.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable

interface SaveMigrationStateUseCase : CompletableUseCase

internal class SaveMigrationStateUseCaseImpl(
    private val sharedPrefManager: SharedPrefManager,
) : SaveMigrationStateUseCase {

    override fun invoke(): Completable =
        Completable.fromAction {
            sharedPrefManager.write(WAS_MIGRATION_COMPLETED, true)
        }
}
