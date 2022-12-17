package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


class SmallLetterValidationStrategyImplTest {

    private val smallLetterValidationStrategy = SmallLetterValidationStrategyImpl()

    @Test
    fun `When password does not contain small letter result is false and score equal to 0`() {
        val expected = PasswordValidationResult(
            validationType = PasswordValidationType.SMALL_LETTERS,
            validationResult = false,
            score = 0,
            maxScore = PasswordValidationType.SMALL_LETTERS.maxScore
        )

        smallLetterValidationStrategy
            .validate("ABCD")
            .test()
            .assertValue(expected)
    }

    @Test
    fun `When password contain small number result is true and score equal to maxScore`() {
        val expected = PasswordValidationResult(
            validationType = PasswordValidationType.SMALL_LETTERS,
            validationResult = true,
            score = PasswordValidationType.SMALL_LETTERS.maxScore,
            maxScore = PasswordValidationType.SMALL_LETTERS.maxScore
        )

        smallLetterValidationStrategy
            .validate("abcd")
            .test()
            .assertValue(expected)
    }
}