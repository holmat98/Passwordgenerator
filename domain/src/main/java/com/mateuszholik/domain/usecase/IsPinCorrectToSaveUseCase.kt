package com.mateuszholik.domain.usecase

import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import com.mateuszholik.domain.validators.PinValidator
import io.reactivex.rxjava3.core.Single

interface IsPinCorrectToSaveUseCase : ParameterizedSingleUseCase<String, Boolean>

internal class IsPinCorrectToSaveUseCaseImpl(
    private val pinValidator: PinValidator
) : IsPinCorrectToSaveUseCase {

    override fun invoke(param: String): Single<Boolean> =
        Single.just(pinValidator.validate(param))
}