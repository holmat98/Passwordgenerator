package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UppercaseLettersValidationStrategyImplTest {

    private val uppercaseLettersValidationStrategy = UppercaseLettersValidationStrategyImpl()

    @Test
    fun `When password does not contain uppercase letter result is false and score equal to 0`() {
        val expected = PasswordValidationResult(
            validationType = PasswordValidationType.UPPERCASE_LETTERS,
            validationResult = false,
            score = 0,
            maxScore = PasswordValidationType.UPPERCASE_LETTERS.maxScore
        )

        uppercaseLettersValidationStrategy
            .validate("abcd")
            .test()
            .assertValue(expected)
    }

    @Test
    fun `When password contain uppercase letter result is true and score equal to maxScore`() {
        val expected = PasswordValidationResult(
            validationType = PasswordValidationType.UPPERCASE_LETTERS,
            validationResult = true,
            score = PasswordValidationType.UPPERCASE_LETTERS.maxScore,
            maxScore = PasswordValidationType.UPPERCASE_LETTERS.maxScore
        )

        uppercaseLettersValidationStrategy
            .validate("Abcd")
            .test()
            .assertValue(expected)
    }
}