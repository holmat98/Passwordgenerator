package com.mateuszholik.domain.usecase

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType
import com.mateuszholik.passwordvalidation.usecases.ValidatePasswordUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Observable
import org.junit.jupiter.api.Test

internal class GetPasswordValidationResultUseCaseImplTest {

    private val validatePasswordUseCase = mockk<ValidatePasswordUseCase>()

    private val getPasswordValidationResultUseCase = GetPasswordValidationResultUseCaseImpl(
        validatePasswordUseCase = validatePasswordUseCase
    )

    @Test
    fun `Observable from ValidatePasswordUseCase is correctly mapped to Single that emits list of PasswordValidationResult`() {
        every { validatePasswordUseCase(PASSWORD) } returns Observable.fromIterable(VALIDATION_RESULT)

        getPasswordValidationResultUseCase(PASSWORD)
            .test()
            .assertComplete()
            .assertValue(VALIDATION_RESULT)
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
