package com.mateuszholik.domain.usecase

import com.mateuszholik.domain.providers.PasswordScoreProvider
import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import io.reactivex.rxjava3.core.Single

interface CalculatePasswordScoreUseCase : ParameterizedSingleUseCase<String, Int>

internal class CalculatePasswordScoreUseCaseImpl(
    private val passwordScoreProvider: PasswordScoreProvider
) : CalculatePasswordScoreUseCase {

    override fun invoke(param: String): Single<Int> =
        Single.just(passwordScoreProvider.getScore(param))
}