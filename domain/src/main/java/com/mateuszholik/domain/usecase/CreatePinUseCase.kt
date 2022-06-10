package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import com.mateuszholik.data.managers.io.SharedPrefKeys.PIN_KEY
import com.mateuszholik.domain.usecase.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable

interface CreatePinUseCase : CompletableUseCase<String>

internal class CreatePinUseCaseImpl(
    private val encryptedSharedPrefManager: EncryptedSharedPrefManager
) : CreatePinUseCase {

    override operator fun invoke(param: String): Completable =
        if (param.length == PIN_CORRECT_LENGTH) {
            Completable.fromAction { encryptedSharedPrefManager.write(PIN_KEY, param) }
        } else {
            Completable.error(Throwable(ERROR_VALUE))
        }

    companion object {
        private const val PIN_CORRECT_LENGTH = 4
        private const val ERROR_VALUE = "Wrong pin length"
    }
}