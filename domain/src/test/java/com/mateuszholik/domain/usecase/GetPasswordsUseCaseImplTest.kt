package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.data.repositories.models.PasswordValidity
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.Test
import com.mateuszholik.data.repositories.models.PasswordInfo

class GetPasswordsUseCaseImplTest {

    private val passwordsRepository = mockk<PasswordsRepository> {
        every { getAllPasswordsInfo() } returns Single.just(listOf(PASSWORD_INFO))
    }

    private val getPasswordUseCase = GetPasswordsUseCaseImpl(
        passwordsRepository = passwordsRepository,
    )

    @Test
    fun `When repository returns all passwords use case will return them correctly`() {
        getPasswordUseCase.invoke()
            .test()
            .assertComplete()
            .assertValue(listOf(PASSWORD_INFO))
    }

    private companion object {
        const val PASSWORD_SCORE = 33
        val PASSWORD_INFO = PasswordInfo(
            id = 0L,
            platformName = "platform",
            passwordScore = PASSWORD_SCORE,
            passwordValidity = PasswordValidity.NeverExpires
        )
    }
}
