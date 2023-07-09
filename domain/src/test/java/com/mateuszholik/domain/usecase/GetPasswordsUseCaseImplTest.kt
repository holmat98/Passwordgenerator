package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.OldPasswordsRepository
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.mappers.PasswordToPasswordTypeMapper
import com.mateuszholik.domain.models.PasswordInfo
import com.mateuszholik.domain.models.PasswordType
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class GetPasswordsUseCaseImplTest {

    private val oldPasswordsRepository = mockk<OldPasswordsRepository> {
        every { getAllPasswords() } returns Single.just(listOf(PASSWORD))
    }

    private val passwordToPasswordTypeMapper = mockk<PasswordToPasswordTypeMapper>()

    private val getPasswordValidationResultUseCase = mockk<GetPasswordValidationResultUseCase>()

    private val getPasswordUseCase = GetPasswordsUseCaseImpl(
        oldPasswordsRepository = oldPasswordsRepository,
        passwordToPasswordTypeMapper = passwordToPasswordTypeMapper,
        getPasswordValidationResultUseCase = getPasswordValidationResultUseCase,
    )

    @Test
    fun `When repository returns all passwords use case will return them correctly`() {
        every {
            getPasswordValidationResultUseCase(PASSWORD.password)
        } returns Single.just(VALIDATION_RESULTS)

        every {
            passwordToPasswordTypeMapper.map(PasswordInfo(PASSWORD, PASSWORD_SCORE))
        } returns VALID_PASSWORD

        getPasswordUseCase.invoke()
            .test()
            .assertComplete()
            .assertValue(listOf(VALID_PASSWORD))
    }

    private companion object {
        val PASSWORD = Password(
            id = 0L,
            platformName = "platform",
            password = "password",
            expiringDate = LocalDateTime.of(2022, 6, 11, 12, 0, 0)
        )
        const val PASSWORD_SCORE = 33
        val VALIDATION_RESULTS = listOf(
            PasswordValidationResult(
                validationType = PasswordValidationType.SPECIAL_CHARACTERS,
                validationResult = false,
                score = 0,
                maxScore = 10
            ),
            PasswordValidationResult(
                validationType = PasswordValidationType.UPPERCASE_LETTERS,
                validationResult = false,
                score = 0,
                maxScore = 10
            ),
            PasswordValidationResult(
                validationType = PasswordValidationType.SMALL_LETTERS,
                validationResult = true,
                score = 10,
                maxScore = 10
            )
        )
        val VALID_PASSWORD = PasswordType.ValidPassword(PASSWORD, PASSWORD_SCORE)
    }
}
