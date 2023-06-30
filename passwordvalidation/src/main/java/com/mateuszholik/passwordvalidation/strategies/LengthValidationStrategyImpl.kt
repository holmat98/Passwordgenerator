package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.LENGTH
import com.mateuszholik.passwordvalidation.utils.Numbers.NONE
import io.reactivex.rxjava3.core.Single

internal class LengthValidationStrategyImpl : PasswordValidationStrategy {

    override fun validate(password: String): Single<PasswordValidationResult> =
        Single.just(
            PasswordValidationResult(
                validationType = LENGTH,
                validationResult = password.length >= MIN_LENGTH,
                score = getPointsForPasswordLength(password),
                maxScore = LENGTH.maxScore
            )
        )

    private fun getPointsForPasswordLength(password: String) =
        when (password.length) {
            in 0..7 -> NONE
            in 8..11 -> LENGTH.maxScore / 3
            in 12..16 -> (LENGTH.maxScore * 2) / 3
            else -> LENGTH.maxScore
        }

    private companion object {
        const val MIN_LENGTH = 8
    }
}
