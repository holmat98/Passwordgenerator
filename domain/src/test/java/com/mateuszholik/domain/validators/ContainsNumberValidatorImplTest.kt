package com.mateuszholik.domain.validators

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ContainsNumberValidatorImplTest(
    private val param: String,
    private val expected: Boolean
) {

    private val containsNumberValidator = ContainsNumberValidatorImpl()

    @Test
    fun validateTest() {
        val result = containsNumberValidator.validate(param)

        assertThat(result).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "ContainsNumberValidatorTest - when text is equal to {0}, validator will return {1}")
        fun data() = listOf(
            arrayOf("test", false),
            arrayOf("TEST", false),
            arrayOf("123", true),
            arrayOf("@#$", false)
        )
    }
}