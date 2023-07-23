package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import com.mateuszholik.data.managers.io.SharedPrefKeys.PIN_KEY
import com.mateuszholik.domain.usecase.base.CompletableParameterizedUseCase
import io.reactivex.rxjava3.core.Completable

interface SavePinUseCase : CompletableParameterizedUseCase<String>

internal class SavePinUseCaseImpl(
    private val encryptedSharedPrefManager: EncryptedSharedPrefManager
) : SavePinUseCase {

    override operator fun invoke(param: String): Completable =
        Completable.fromAction { encryptedSharedPrefManager.write(PIN_KEY, param) }
}
