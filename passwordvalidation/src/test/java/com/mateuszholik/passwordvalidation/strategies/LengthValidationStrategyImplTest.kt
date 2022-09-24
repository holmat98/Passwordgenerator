package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LengthValidationStrategyImplTest {

    private val lengthValidationStrategy = LengthValidationStrategyImpl()

    @Test
    fun `When password has less digits than 8 result is false`() {
        val expected = PasswordValidationResult(
            validationType = PasswordValidationType.LENGTH,
            validationResult = false,
            score = 0,
            maxScore = PasswordValidationType.LENGTH.maxScore
        )

        lengthValidationStrategy
            .validate("abcd")
            .test()
            .assertValue(expected)
    }

    @Test
    fun `When password has more digits than 8 result is true and score is equal to half of maxScore`() {
        val expected = PasswordValidationResult(
            validationType = PasswordValidationType.LENGTH,
            validationResult = true,
            score = PasswordValidationType.LENGTH.maxScore/2,
            maxScore = PasswordValidationType.LENGTH.maxScore
        )

        lengthValidationStrategy
            .validate("abcd12345")
            .test()
            .assertValue(expected)
    }

    @Test
    fun `When password has more digits than 12 score is equal to maxScore`() {
        val expected = PasswordValidationResult(
            validationType = PasswordValidationType.LENGTH,
            validationResult = true,
            score = PasswordValidationType.LENGTH.maxScore,
            maxScore = PasswordValidationType.LENGTH.maxScore
        )

        lengthValidationStrategy
            .validate("abcd12345abcd")
            .test()
            .assertValue(expected)
    }
}