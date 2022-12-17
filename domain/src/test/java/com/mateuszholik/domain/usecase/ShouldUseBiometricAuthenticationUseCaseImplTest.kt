package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.SharedPrefManager
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


class ShouldUseBiometricAuthenticationUseCaseImplTest {

    private val sharedPrefManager = mockk<SharedPrefManager>()

    private val shouldUseBiometricAuthenticationUseCase =
        ShouldUseBiometricAuthenticationUseCaseImpl(sharedPrefManager)

    @Test
    fun `When should use biometric auth use case will return true`() {
        every { sharedPrefManager.readBoolean(any()) } returns true

        shouldUseBiometricAuthenticationUseCase.invoke()
            .test()
            .assertValue(true)
    }

    @Test
    fun `When should use biometric auth use case will return false`() {
        every { sharedPrefManager.readBoolean(any()) } returns false

        shouldUseBiometricAuthenticationUseCase.invoke()
            .test()
            .assertValue(false)
    }
}