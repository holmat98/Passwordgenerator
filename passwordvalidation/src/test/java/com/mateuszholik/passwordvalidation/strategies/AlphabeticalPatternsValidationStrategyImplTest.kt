package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.ALPHABETICAL_PATTERN
import org.junit.jupiter.api.Test

class AlphabeticalPatternsValidationStrategyImplTest {

    private val alphabeticalPatternsValidationStrategy =
        AlphabeticalPatternsValidationStrategyImpl()

    @Test
    fun `When a password has zero alphabetical patterns result is true and score is equal to the maxScore`() {
        alphabeticalPatternsValidationStrategy
            .validate(ZERO_PATTERNS_PASSWORD)
            .test()
            .assertValue(
                PasswordValidationResult(
                    validationType = ALPHABETICAL_PATTERN,
                    validationResult = true,
                    score = ALPHABETICAL_PATTERN.maxScore,
                    maxScore = ALPHABETICAL_PATTERN.maxScore
                )
            )
    }

    @Test
    fun `When a password has one alphabetical patterns result is true and score is equal to the half of the maxScore`() {
        alphabeticalPatternsValidationStrategy
            .validate(ONE_PATTERN_PASSWORD)
            .test()
            .assertValue(
                PasswordValidationResult(
                    validationType = ALPHABETICAL_PATTERN,
                    validationResult = true,
                    score = ALPHABETICAL_PATTERN.maxScore / 2,
                    maxScore = ALPHABETICAL_PATTERN.maxScore
                )
            )
    }

    @Test
    fun `When a password has two alphabetical patterns result is false and score is equal to zero`() {
        alphabeticalPatternsValidationStrategy
            .validate(TWO_PATTERNS_PASSWORD)
            .test()
            .assertValue(
                PasswordValidationResult(
                    validationType = ALPHABETICAL_PATTERN,
                    validationResult = false,
                    score = 0,
                    maxScore = ALPHABETICAL_PATTERN.maxScore
                )
            )
    }

    private companion object {
        const val ZERO_PATTERNS_PASSWORD = "adhlqz"
        const val ONE_PATTERN_PASSWORD = "aahlqz"
        const val TWO_PATTERNS_PASSWORD = "aahlyz"
    }
}