package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.data.managers.io.SharedPrefKeys.PASSWORD_VALIDITY
import com.mateuszholik.domain.usecase.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable

interface SavePasswordValidityValueUseCase : CompletableUseCase<Long>

internal class SavePasswordValidityValueUseCaseImpl(
    private val sharedPrefManager: SharedPrefManager
) : SavePasswordValidityValueUseCase {

    override fun invoke(param: Long): Completable =
        Completable.fromAction { sharedPrefManager.write(PASSWORD_VALIDITY, param) }
}