package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.data.repositories.models.Password
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Maybe
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class GetPasswordUseCaseImplTest {

    private val passwordsRepository = mockk<PasswordsRepository>()
    private val getPasswordUseCase = GetPasswordUseCaseImpl(passwordsRepository)

    @Test
    fun `When repository correctly returned password then use case will return this password`() {
        every {
            passwordsRepository.getPassword(ID)
        } returns Maybe.just(PASSWORD)

        getPasswordUseCase(ID)
            .test()
            .assertValue(PASSWORD)
    }

    @Test
    fun `When repository did not return password then use case will return empty`() {
        every {
            passwordsRepository.getPassword(ID)
        } returns Maybe.empty()

        getPasswordUseCase(ID)
            .test()
            .assertNoValues()
    }

    private companion object {
        const val ID = 1L
        val PASSWORD = Password(
            id = ID,
            password = "password",
            platformName = "platform",
            expiringDate = LocalDateTime.of(2022, 12, 23, 12, 0, 0)
        )
    }
}