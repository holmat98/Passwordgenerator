package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import com.mateuszholik.data.managers.io.SharedPrefKeys.PIN_KEY
import com.mateuszholik.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Single

interface IsPinCreatedUseCase : UseCase<Boolean>

internal class IsPinCreatedUseCaseImpl(
    private val encryptedSharedPrefManager: EncryptedSharedPrefManager
) : IsPinCreatedUseCase {

    override fun invoke(): Single<Boolean> =
        Single.just(
            encryptedSharedPrefManager.readString(PIN_KEY)
                .isNullOrEmpty()
                .not()
        )
}