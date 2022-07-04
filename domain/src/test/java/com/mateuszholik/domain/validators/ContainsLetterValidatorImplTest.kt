package com.mateuszholik.domain.validators

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ContainsLetterValidatorImplTest(
    private val param: String,
    private val expected: Boolean
) {

    private val containsLetterValidator = ContainsLetterValidatorImpl()

    @Test
    fun validateTest() {
        val result = containsLetterValidator.validate(param)

        assertThat(result).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "ContainsLetterValidatorTest - when text is equal to {0}, validator will return {1}")
        fun data() = listOf(
            arrayOf("test", true),
            arrayOf("TEST", false),
            arrayOf("123", false),
            arrayOf("@#$", false)
        )
    }
}