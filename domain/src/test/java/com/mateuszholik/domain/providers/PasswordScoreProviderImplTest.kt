package com.mateuszholik.domain.providers

import com.mateuszholik.domain.validators.ContainsLetterValidatorImpl
import com.mateuszholik.domain.validators.ContainsUpperCaseValidatorImpl
import com.mateuszholik.domain.validators.ContainsNumberValidatorImpl
import com.mateuszholik.domain.validators.ContainsSpecialCharacterValidatorImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PasswordScoreProviderImplTest(
    private val param: String,
    private val expected: Int
) {

    private val containsLetterValidator = ContainsLetterValidatorImpl()
    private val containsUpperCaseValidator = ContainsUpperCaseValidatorImpl()
    private val containsNumberValidator = ContainsNumberValidatorImpl()
    private val containsSpecialCharacterValidator = ContainsSpecialCharacterValidatorImpl()
    private val passwordScoreManagerImpl = PasswordScoreProviderImpl(
        containsLetterValidator,
        containsUpperCaseValidator,
        containsNumberValidator,
        containsSpecialCharacterValidator
    )

    @Test
    fun validateTest() {
        val result = passwordScoreManagerImpl.getScore(param)

        assertThat(result).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "ContainsLetterValidatorTest - when text is equal to {0}, validator will return {1}")
        fun data() = listOf(
            arrayOf("abcd", 1),
            arrayOf("abcd1234", 6),
            arrayOf("aD1#", 10),
            arrayOf("aD1#sd893", 39),
            arrayOf("LfT$0!-!+F#SxU&SQ", 74)
        )
    }
}