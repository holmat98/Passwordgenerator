package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.LENGTH
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

class LengthValidationStrategyImplTest {

    private val lengthValidationStrategy = LengthValidationStrategyImpl()

    private data class TestCase(
        val password: String,
        val score: Int,
        val expectedResult: Boolean,
    )

    @TestFactory
    fun testLengthValidation() =
        listOf(
            TestCase(
                password = "abcd",
                score = 0,
                expectedResult = false
            ),
            TestCase(
                password = "abcd12345",
                score = LENGTH.maxScore / 3,
                expectedResult = true
            ),
            TestCase(
                password = "abcd12345@#$%",
                score = (LENGTH.maxScore * 2) / 3,
                expectedResult = true
            ),
            TestCase(
                password = "abcd12345@#$%^&*(",
                score = LENGTH.maxScore,
                expectedResult = true
            )
        ).map {
            dynamicTest(
                "When ${it.password} with length ${it.password.length} is validated the result is ${it.score} out of ${LENGTH.maxScore} and ${it.expectedResult}"
            ) {
                val expected = PasswordValidationResult(
                    validationType = LENGTH,
                    validationResult = it.expectedResult,
                    score = it.score,
                    maxScore = LENGTH.maxScore
                )

                lengthValidationStrategy
                    .validate(it.password)
                    .test()
                    .assertValue(expected)
            }
        }
}
