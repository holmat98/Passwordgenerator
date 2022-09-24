package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NumberValidationStrategyImplTest {

    private val numberValidationStrategyImpl = NumberValidationStrategyImpl()

    @Test
    fun `When password does not contain number result is false and score equal to 0`() {
        val expected = PasswordValidationResult(
            validationType = PasswordValidationType.NUMBERS,
            validationResult = false,
            score = 0,
            maxScore = PasswordValidationType.NUMBERS.maxScore
        )

        numberValidationStrategyImpl
            .validate("abcd")
            .test()
            .assertValue(expected)
    }

    @Test
    fun `When password contain number result is true and score equal to maxScore`() {
        val expected = PasswordValidationResult(
            validationType = PasswordValidationType.NUMBERS,
            validationResult = true,
            score = PasswordValidationType.NUMBERS.maxScore,
            maxScore = PasswordValidationType.NUMBERS.maxScore
        )

        numberValidationStrategyImpl
            .validate("abcd12345")
            .test()
            .assertValue(expected)
    }
}