package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import com.mateuszholik.domain.constants.Constants.EMPTY_STRING
import com.mateuszholik.domain.constants.SharedPrefKeys.PIN_KEY
import com.mateuszholik.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Single

interface ShouldSkipOnBoardingUseCase : UseCase<Boolean>

internal class ShouldSkipOnBoardingUseCaseImpl(
    private val encryptedSharedPrefManager: EncryptedSharedPrefManager
) : ShouldSkipOnBoardingUseCase {

    override fun invoke(): Single<Boolean> =
        Single.just(encryptedSharedPrefManager.readString(PIN_KEY) ?: EMPTY_STRING)
            .map { !it.isNullOrEmpty() }
}