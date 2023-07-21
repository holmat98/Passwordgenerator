package com.mateuszholik.domain.usecase

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.Test

internal class GetPasswordScoreUseCaseImplTest {

    private val getPasswordValidationResultUseCase = mockk<GetPasswordValidationResultUseCase>()

    private val getPasswordScoreUseCase = GetPasswordScoreUseCaseImpl(
        getPasswordValidationResultUseCase = getPasswordValidationResultUseCase
    )

    @Test
    fun `PasswordScore is equal to 50 based on the result from the GetPasswordValidationResultUseCase`() {
        every {
            getPasswordValidationResultUseCase(PASSWORD)
        } returns Single.just(VALIDATION_RESULT)

        getPasswordScoreUseCase(PASSWORD)
            .test()
            .assertComplete()
            .assertValue(50)
    }

    @Test
    fun `When GetPasswordValidationResultUseCase returns error the password score is equal to 0`() {
        every {
            getPasswordValidationResultUseCase(PASSWORD)
        } returns Single.error(Exception("Something went wrong"))

        getPasswordScoreUseCase(PASSWORD)
            .test()
            .assertComplete()
            .assertValue(0)
    }

    private companion object {
        const val PASSWORD = "password"
        val VALIDATION_RESULT = listOf(
            PasswordValidationResult(
                validationType = PasswordValidationType.LENGTH,
                validationResult = true,
                score = 10,
                maxScore = 20
            ),
            PasswordValidationResult(
                validationType = PasswordValidationType.SMALL_LETTERS,
                validationResult = true,
                score = 5,
                maxScore = 10
            )
        )
    }
}
