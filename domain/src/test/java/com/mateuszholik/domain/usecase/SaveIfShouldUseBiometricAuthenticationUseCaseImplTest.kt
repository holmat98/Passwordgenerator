package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import com.mateuszholik.data.managers.io.SharedPrefKeys
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class SaveIfShouldUseBiometricAuthenticationUseCaseImplTest {

    private val encryptedSharedPrefManager = mockk<EncryptedSharedPrefManager> {
        every { write(SharedPrefKeys.SHOULD_USE_BIOMETRIC_AUTH, any<Boolean>()) } returns Unit
    }
    private val saveIfShouldUseBiometricAuthenticationUseCase =
        SaveIfShouldUseBiometricAuthenticationUseCaseImpl(encryptedSharedPrefManager)

    @Test
    fun `When param is true then this value is saved in shared prefs with true value`() {
        saveIfShouldUseBiometricAuthenticationUseCase(true)
            .test()
            .assertComplete()

        verify {
            encryptedSharedPrefManager.write(SharedPrefKeys.SHOULD_USE_BIOMETRIC_AUTH, true)
        }
    }

    @Test
    fun `When param is false then this value is saved in shared prefs with false value`() {
        saveIfShouldUseBiometricAuthenticationUseCase(false)
            .test()
            .assertComplete()

        verify {
            encryptedSharedPrefManager.write(SharedPrefKeys.SHOULD_USE_BIOMETRIC_AUTH, false)
        }
    }
}