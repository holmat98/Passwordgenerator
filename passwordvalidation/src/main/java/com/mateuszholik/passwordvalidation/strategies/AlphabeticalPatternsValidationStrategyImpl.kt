package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.ALPHABETICAL_PATTERN
import com.mateuszholik.passwordvalidation.utils.Numbers.INVALID_DISTANCE
import com.mateuszholik.passwordvalidation.utils.Numbers.MAX_NUM_OF_PATTERNS
import com.mateuszholik.passwordvalidation.utils.Numbers.NONE
import io.reactivex.rxjava3.core.Single
import kotlin.math.abs

internal class AlphabeticalPatternsValidationStrategyImpl : PasswordValidationStrategy {

    override fun validate(password: String): Single<PasswordValidationResult> =
        Single.fromCallable {
            var patternsCounter = 0

            for (index in 0 until password.length - 1) {
                if (abs(password[index].code - password[index + 1].code) in INVALID_DISTANCE) {
                    patternsCounter += 1
                }
            }

            PasswordValidationResult(
                validationType = ALPHABETICAL_PATTERN,
                validationResult = patternsCounter <= MAX_NUM_OF_PATTERNS,
                score = getScore(patternsCounter),
                maxScore = ALPHABETICAL_PATTERN.maxScore
            )
        }

    private fun getScore(patternsCounter: Int): Int =
        when (patternsCounter) {
            NONE -> ALPHABETICAL_PATTERN.maxScore
            MAX_NUM_OF_PATTERNS -> ALPHABETICAL_PATTERN.maxScore / 2
            else -> NONE
        }
}