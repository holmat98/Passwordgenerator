package com.mateuszholik.domain.validators

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PasswordLengthValidatorImplTest(
    private val param: String,
    private val expected: Boolean
) {

    private val passwordLengthValidator = PasswordLengthValidatorImpl()

    @Test
    fun validateTest() {
        val result = passwordLengthValidator.validate(param)

        assertThat(result).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "PasswordLengthValidatorTest - when password is equal to {0}, validator will return {1}")
        fun data() = listOf(
            arrayOf("test", false),
            arrayOf("TEST", false),
            arrayOf("123", false),
            arrayOf("@#$", false),
            arrayOf("Abcd12445@$", true)
        )
    }
}