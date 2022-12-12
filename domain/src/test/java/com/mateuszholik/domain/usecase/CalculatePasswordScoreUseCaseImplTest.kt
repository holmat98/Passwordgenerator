package com.mateuszholik.domain.usecase

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test


class CalculatePasswordScoreUseCaseImplTest {

    /*private val passwordScoreManager = mockk<PasswordScoreProvider>()

    private val calculatePasswordScoreUseCase =
        CalculatePasswordScoreUseCaseImpl(passwordScoreManager)

    @Test
    fun `Use case will return single with correctly calculated password score`() {
        every { passwordScoreManager.getScore(PASSWORD) } returns PASSWORD_SCORE

        calculatePasswordScoreUseCase.invoke(PASSWORD)
            .test()
            .assertComplete()
            .assertValue(PASSWORD_SCORE)
    }*/

    private companion object {
        const val PASSWORD = "Password1234"
        const val PASSWORD_SCORE = 21
    }
}