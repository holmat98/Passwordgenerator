package com.mateuszholik.domain.usecase

import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import io.reactivex.rxjava3.core.Single

interface GetPasswordScoreUseCase : ParameterizedSingleUseCase<String, Int>

internal class GetPasswordScoreUseCaseImpl(
    private val getPasswordValidationResultUseCase: GetPasswordValidationResultUseCase,
) : GetPasswordScoreUseCase {

    override fun invoke(param: String): Single<Int> =
        getPasswordValidationResultUseCase(param)
            .map { validationResult ->
                val score = validationResult.sumOf { it.score }
                val maxScore = validationResult.sumOf { it.maxScore }

                ((score.toFloat() / maxScore.toFloat()) * 100).toInt()
            }
            .onErrorReturn { 0 }
}
