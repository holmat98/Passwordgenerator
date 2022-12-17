package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.data.repositories.models.Password
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDateTime

class GetPasswordsUseCaseImplTest {

    /*private val passwordsRepository = mockk<PasswordsRepository> {
        every { getAllPasswords() } returns Single.just(listOf(PASSWORD))
    }

    private val getPasswordUseCase = GetPasswordsUseCaseImpl(passwordsRepository)

    @Test
    fun `When repository returns all passwords use case will return them correctly`() {
        getPasswordUseCase.invoke()
            .test()
            .assertComplete()
            .assertValue(listOf(PASSWORD))

        verify(exactly = 1) { passwordsRepository.getAllPasswords() }
    }*/

    private companion object {
        val PASSWORD = Password(
            id = 0L,
            platformName = "platform",
            password = "password",
            expiringDate = LocalDateTime.of(2022, 6, 11, 12, 0, 0)
        )
    }
}