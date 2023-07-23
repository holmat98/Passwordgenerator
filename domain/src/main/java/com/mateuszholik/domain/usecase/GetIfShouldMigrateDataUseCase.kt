package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.SharedPrefKeys.WAS_MIGRATION_COMPLETED
import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Single

interface GetIfShouldMigrateDataUseCase : UseCase<Boolean>

internal class GetIfShouldMigrateDataUseCaseImpl(
    private val sharedPrefManager: SharedPrefManager
) : GetIfShouldMigrateDataUseCase {

    override fun invoke(): Single<Boolean> =
        Single.just(sharedPrefManager.readBoolean(WAS_MIGRATION_COMPLETED))
}
