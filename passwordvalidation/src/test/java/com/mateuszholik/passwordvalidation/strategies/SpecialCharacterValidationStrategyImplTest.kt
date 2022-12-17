package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


class SpecialCharacterValidationStrategyImplTest {

    private val specialCharacterValidationStrategy = SpecialCharacterValidationStrategyImpl()

    @Test
    fun `When password does not contain special character result is false and score equal to 0`() {
        val expected = PasswordValidationResult(
            validationType = PasswordValidationType.SPECIAL_CHARACTERS,
            validationResult = false,
            score = 0,
            maxScore = PasswordValidationType.SPECIAL_CHARACTERS.maxScore
        )

        specialCharacterValidationStrategy
            .validate("abcd")
            .test()
            .assertValue(expected)
    }

    @Test
    fun `When password contain number result is true and score equal to maxScore`() {
        val expected = PasswordValidationResult(
            validationType = PasswordValidationType.SPECIAL_CHARACTERS,
            validationResult = true,
            score = PasswordValidationType.SPECIAL_CHARACTERS.maxScore,
            maxScore = PasswordValidationType.SPECIAL_CHARACTERS.maxScore
        )

        specialCharacterValidationStrategy
            .validate("@bcd")
            .test()
            .assertValue(expected)
    }
}