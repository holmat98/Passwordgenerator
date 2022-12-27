package com.mateuszholik.domain.usecase

import com.mateuszholik.domain.validators.PinValidator
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class IsPinCorrectToSaveUseCaseImplTest {

    private val pinValidator = mockk<PinValidator>()
    private val isPinCorrectToSaveUseCase = IsPinCorrectToSaveUseCaseImpl(pinValidator)

    @TestFactory
    fun checkIsPinCorrectToBeSaved() =
        listOf(
            false to false,
            true to true
        ).map { (validatedValue, expectedResult) ->
            dynamicTest("When validator returned $validatedValue then use case returns $expectedResult") {
                every {
                    pinValidator.validate(PIN)
                } returns validatedValue

                isPinCorrectToSaveUseCase(PIN)
                    .test()
                    .assertValue(expectedResult)
            }
        }

    private companion object {
        const val PIN = "1234"
    }
}