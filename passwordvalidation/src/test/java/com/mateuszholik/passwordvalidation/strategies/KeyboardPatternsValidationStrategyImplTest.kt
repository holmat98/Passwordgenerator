package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.KEYBOARD_PATTERN
import org.junit.jupiter.api.Test

class KeyboardPatternsValidationStrategyImplTest {

    private val keyboardPatternsValidationStrategy = KeyboardPatternsValidationStrategyImpl()

    @Test
    fun `When a password has zero keyboard patterns result is true and score is equal to the maxScore`() {
        keyboardPatternsValidationStrategy
            .validate(ZERO_PATTERNS_PASSWORD)
            .test()
            .assertValue(
                PasswordValidationResult(
                    validationType = KEYBOARD_PATTERN,
                    validationResult = true,
                    score = KEYBOARD_PATTERN.maxScore,
                    maxScore = KEYBOARD_PATTERN.maxScore
                )
            )
    }

    @Test
    fun `When a password has one keyboard patterns result is true and score is equal to the half of the maxScore`() {
        keyboardPatternsValidationStrategy
            .validate(ONE_PATTERN_PASSWORD)
            .test()
            .assertValue(
                PasswordValidationResult(
                    validationType = KEYBOARD_PATTERN,
                    validationResult = true,
                    score = KEYBOARD_PATTERN.maxScore / 2,
                    maxScore = KEYBOARD_PATTERN.maxScore
                )
            )
    }

    @Test
    fun `When a password has two keyboard patterns result is false and score is equal to zero`() {
        keyboardPatternsValidationStrategy
            .validate(TWO_PATTERNS_PASSWORD)
            .test()
            .assertValue(
                PasswordValidationResult(
                    validationType = KEYBOARD_PATTERN,
                    validationResult = false,
                    score = 0,
                    maxScore = KEYBOARD_PATTERN.maxScore
                )
            )
    }

    private companion object {
        const val ZERO_PATTERNS_PASSWORD = "qhp4cm"
        const val ONE_PATTERN_PASSWORD = "qwp4cm"
        const val TWO_PATTERNS_PASSWORD = "qwp46m"
    }
}