package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.BooleanTestCase
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.COMMON_NAME
import com.mateuszholik.passwordvalidation.usecases.GetIsPasswordANameUseCase
import com.mateuszholik.passwordvalidation.usecases.GetIsPasswordAPetNameUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class CommonNameValidationStrategyImplTest {

    private val getIsPasswordANameUseCase = mockk<GetIsPasswordANameUseCase>()
    private val getIsPasswordAPetNameUseCase = mockk<GetIsPasswordAPetNameUseCase>()

    private val commonNameValidationStrategy = CommonNameValidationStrategyImpl(
        getIsPasswordANameUseCase = getIsPasswordANameUseCase,
        getIsPasswordAPetNameUseCase = getIsPasswordAPetNameUseCase
    )

    @TestFactory
    fun checkCommonNames() =
        listOf(
            BooleanTestCase(firstArgument = true, secondArgument = true, expected = true),
            BooleanTestCase(firstArgument = true, secondArgument = false, expected = false),
            BooleanTestCase(firstArgument = false, secondArgument = true, expected = false),
            BooleanTestCase(firstArgument = false, secondArgument = false, expected = false)
        ).map { testCase ->
            dynamicTest(
                "When password ${testCase.firstArgument.isOrIsntText} a name" +
                        " and ${testCase.secondArgument.isOrIsntText} a pet name " +
                        "the result is ${testCase.expected}"
            ) {
                every {
                    getIsPasswordANameUseCase(PASSWORD)
                } returns Single.just(testCase.firstArgument)

                every {
                    getIsPasswordAPetNameUseCase(PASSWORD)
                } returns Single.just(testCase.secondArgument)

                commonNameValidationStrategy
                    .validate(PASSWORD)
                    .test()
                    .assertValue(
                        PasswordValidationResult(
                            validationType = COMMON_NAME,
                            validationResult = testCase.expected,
                            score = if (testCase.expected) COMMON_NAME.maxScore else 0,
                            maxScore = COMMON_NAME.maxScore
                        )
                    )
            }
        }

    private val Boolean.isOrIsntText: String
        get() = if (this) "is" else "isn't"

    private companion object {
        const val PASSWORD = "maciek"
    }
}