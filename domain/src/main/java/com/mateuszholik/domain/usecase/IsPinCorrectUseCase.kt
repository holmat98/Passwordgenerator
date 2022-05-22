package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import com.mateuszholik.data.extensions.read
import com.mateuszholik.domain.constants.Constants.EMPTY_STRING
import com.mateuszholik.domain.constants.SharedPrefKeys.PIN_KEY
import com.mateuszholik.domain.models.PinState
import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import io.reactivex.rxjava3.core.Single

interface IsPinCorrectUseCase : ParameterizedSingleUseCase<String, PinState>

internal class IsPinCorrectUseCaseImpl(
    private val encryptedSharedPrefManager: EncryptedSharedPrefManager
) : IsPinCorrectUseCase {

    override fun invoke(param: String): Single<PinState> =
        Single.just(encryptedSharedPrefManager.read<String>(PIN_KEY) ?: EMPTY_STRING)
            .map {
                if (param == it) {
                    PinState.CORRECT
                } else {
                    PinState.WRONG
                }
            }
}