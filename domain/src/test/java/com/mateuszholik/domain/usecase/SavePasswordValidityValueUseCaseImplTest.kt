package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.SharedPrefKeys
import com.mateuszholik.data.managers.io.SharedPrefManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class SavePasswordValidityValueUseCaseImplTest {

    private val sharedPrefManager = mockk<SharedPrefManager>()

    private val savePasswordValidityValueUseCase =
        SavePasswordValidityValueUseCaseImpl(sharedPrefManager)

    @Test
    fun `When param is 90 then this value is saved in shared prefs`() {
        every {
            sharedPrefManager.write(SharedPrefKeys.PASSWORD_VALIDITY, PASSWORD_VALIDITY)
        } returns Unit

        savePasswordValidityValueUseCase(PASSWORD_VALIDITY)
            .test()
            .assertComplete()

        verify {
            sharedPrefManager.write(SharedPrefKeys.PASSWORD_VALIDITY, PASSWORD_VALIDITY)
        }
    }

    private companion object {
        const val PASSWORD_VALIDITY = 90L
    }
}
