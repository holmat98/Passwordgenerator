package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.data.managers.io.SharedPrefKeys.PASSWORD_VALIDITY
import com.mateuszholik.domain.usecase.base.CompletableParameterizedUseCase
import io.reactivex.rxjava3.core.Completable

interface SavePasswordValidityValueUseCase : CompletableParameterizedUseCase<Long>

internal class SavePasswordValidityValueUseCaseImpl(
    private val sharedPrefManager: SharedPrefManager
) : SavePasswordValidityValueUseCase {

    override fun invoke(param: Long): Completable =
        Completable.fromAction { sharedPrefManager.write(PASSWORD_VALIDITY, param) }
}
