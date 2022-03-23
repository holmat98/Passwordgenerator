package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import com.mateuszholik.data.extensions.read
import com.mateuszholik.domain.constants.Constants.EMPTY_STRING
import com.mateuszholik.domain.constants.SharedPrefKeys.PIN_KEY
import com.mateuszholik.domain.usecase.base.ParameterizedUseCase
import io.reactivex.rxjava3.core.Single

interface IsPinCorrectUseCase : ParameterizedUseCase<String, Boolean>

internal class IsPinCorrectUseCaseImpl(
    private val encryptedSharedPrefManager: EncryptedSharedPrefManager
) : IsPinCorrectUseCase {

    override fun invoke(param: String): Single<Boolean> =
        Single.just(encryptedSharedPrefManager.read<String>(PIN_KEY) ?: EMPTY_STRING)
            .map { param == it }
}