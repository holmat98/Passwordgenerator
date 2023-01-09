package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.SMALL_LETTERS
import com.mateuszholik.passwordvalidation.utils.Numbers.NONE
import io.reactivex.rxjava3.core.Single

class SmallLetterValidationStrategyImpl : PasswordValidationStrategy {

    override fun validate(password: String): Single<PasswordValidationResult> =
        Single.just(LETTER_REGEX.containsMatchIn(password))
            .map { hasSmallLetter ->
                PasswordValidationResult(
                    validationType = SMALL_LETTERS,
                    validationResult = hasSmallLetter,
                    score = if (hasSmallLetter) SMALL_LETTERS.maxScore else NONE,
                    maxScore = SMALL_LETTERS.maxScore
                )
            }

    private companion object {
        val LETTER_REGEX = """.*[a-z].*""".toRegex()
    }
}