package com.mateuszholik.domain.usecase

import com.mateuszholik.domain.models.PasswordScore
import com.mateuszholik.domain.validators.*
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatePasswordUseCaseImplTest {

    private val containsLetterValidator = mockk<ContainsLetterValidator> {
        every { validate(PASSWORD1) } returns true
    }
    private val containsUpperCaseValidator = mockk<ContainsUpperCaseValidator> {
        every { validate(PASSWORD1) } returns true
    }
    private val containsNumberValidator = mockk<ContainsNumberValidator> {
        every { validate(PASSWORD1) } returns true
    }
    private val containsSpecialCharacterValidator = mockk<ContainsSpecialCharacterValidator> {
        every { validate(PASSWORD1) } returns false
    }
    private val passwordLengthValidator = mockk<PasswordLengthValidator> {
        every { validate(PASSWORD1) } returns true
    }

    private val validatePasswordUseCase = ValidatePasswordUseCaseImpl(
        containsLetterValidator,
        containsUpperCaseValidator,
        containsNumberValidator,
        containsSpecialCharacterValidator,
        passwordLengthValidator
    )

    @Test
    fun `ValidatePasswordUseCase will return expected value`() {
        validatePasswordUseCase.invoke(PASSWORD1)
            .test()
            .assertValue(PASSWORD_SCORE)
    }

    private companion object {
        const val PASSWORD1 = "Abcd2233"
        val PASSWORD_SCORE = PasswordScore(
            containsLetters = true,
            containsUpperCaseLetters = true,
            containsNumbers = true,
            containsSpecialCharacters = false,
            hasMinimumLength = true
        )
    }
}