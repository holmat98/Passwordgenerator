package com.mateuszholik.domain.validators

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ContainsUpperCaseValidatorImplTest(
    private val param: String,
    private val expected: Boolean
) {

    private val containsUppercaseLetterValidator = ContainsUpperCaseValidatorImpl()

    @Test
    fun validateTest() {
        val result = containsUppercaseLetterValidator.validate(param)

        assertThat(result).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "ContainsUppercaseValidatorTest - when text is equal to {0}, validator will return {1}")
        fun data() = listOf(
            arrayOf("test", false),
            arrayOf("TEST", true),
            arrayOf("123", false),
            arrayOf("@#$", false)
        )
    }
}