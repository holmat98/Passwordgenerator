package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import com.mateuszholik.data.managers.io.SharedPrefKeys.PIN_KEY
import com.mateuszholik.domain.models.PinState
import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import io.reactivex.rxjava3.core.Single

interface IsPinCorrectUseCase : ParameterizedSingleUseCase<String, PinState>

internal class IsPinCorrectUseCaseImpl(
    private val encryptedSharedPrefManager: EncryptedSharedPrefManager
) : IsPinCorrectUseCase {

    override fun invoke(param: String): Single<PinState> =
        Single.fromCallable {
            encryptedSharedPrefManager.readString(PIN_KEY)?.let { pin ->
                if (param == pin) {
                    PinState.CORRECT
                } else {
                    PinState.WRONG
                }
            } ?: PinState.WRONG
        }
}