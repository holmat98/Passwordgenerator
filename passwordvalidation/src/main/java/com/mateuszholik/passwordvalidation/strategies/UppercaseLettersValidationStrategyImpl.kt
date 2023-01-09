package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.UPPERCASE_LETTERS
import com.mateuszholik.passwordvalidation.utils.Numbers.NONE
import io.reactivex.rxjava3.core.Single

class UppercaseLettersValidationStrategyImpl : PasswordValidationStrategy {

    override fun validate(password: String): Single<PasswordValidationResult> =
        Single.just(UPPERCASE_REGEX.containsMatchIn(password)).map { hasUppercase ->
            PasswordValidationResult(
                validationType = UPPERCASE_LETTERS,
                validationResult = hasUppercase,
                score = if (hasUppercase) UPPERCASE_LETTERS.maxScore else NONE,
                maxScore = UPPERCASE_LETTERS.maxScore
            )
        }

        private companion object {
            val UPPERCASE_REGEX = """.*[A-Z].*""".toRegex()
        }
}