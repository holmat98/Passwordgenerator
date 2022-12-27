package com.mateuszholik.domain.validators

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class PinValidatorImplTest {

    private val pinValidator = PinValidatorImpl()

    @TestFactory
    fun checkPinValidator() =
        listOf(
            "1234" to true,
            "12" to false,
            "" to false,
            "12356" to false,
            "1" to false,
            "123" to false
        ).map { (pin, expected) ->
            dynamicTest("When pin is $pin the result is $expected") {
                val result = pinValidator.validate(pin)

                assertThat(result).isEqualTo(expected)
            }
        }
}