package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.NUMBERS
import com.mateuszholik.passwordvalidation.utils.Numbers.NONE
import io.reactivex.rxjava3.core.Single

internal class NumberValidationStrategyImpl : PasswordValidationStrategy {

    override fun validate(password: String): Single<PasswordValidationResult> =
        Single.just(NUMBER_REGEX.containsMatchIn(password)).map { hasNumber ->
                PasswordValidationResult(
                    validationType = NUMBERS,
                    validationResult = hasNumber,
                    score = if (hasNumber) NUMBERS.maxScore else NONE,
                    maxScore = NUMBERS.maxScore
                )
            }

    private companion object {
        val NUMBER_REGEX = """.*[0-9].*""".toRegex()
    }
}
