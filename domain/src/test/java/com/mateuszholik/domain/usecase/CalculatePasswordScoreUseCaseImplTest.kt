package com.mateuszholik.domain.usecase

import com.mateuszholik.domain.managers.PasswordScoreManager
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CalculatePasswordScoreUseCaseImplTest {

    private val passwordScoreManager = mockk<PasswordScoreManager>()

    private val calculatePasswordScoreUseCase =
        CalculatePasswordScoreUseCaseImpl(passwordScoreManager)

    @Test
    fun `Use case will return single with correctly calculated password score`() {
        every { passwordScoreManager.getScore(PASSWORD) } returns PASSWORD_SCORE

        calculatePasswordScoreUseCase.invoke(PASSWORD)
            .test()
            .assertComplete()
            .assertValue(PASSWORD_SCORE)
    }

    private companion object {
        const val PASSWORD = "Password1234"
        const val PASSWORD_SCORE = 21
    }
}