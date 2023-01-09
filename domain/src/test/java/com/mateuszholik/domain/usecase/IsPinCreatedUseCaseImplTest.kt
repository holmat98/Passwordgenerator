package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import com.mateuszholik.data.managers.io.SharedPrefKeys.PIN_KEY
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class IsPinCreatedUseCaseImplTest {

    private val encryptedSharedPrefManager = mockk<EncryptedSharedPrefManager>()

    private val isPinCreatedUseCase = IsPinCreatedUseCaseImpl(encryptedSharedPrefManager)

    @Test
    fun `When pin is created use case will return true`() {
        setUpSharedPrefReturn(PIN)

        isPinCreatedUseCase.invoke()
            .test()
            .assertValue(true)
    }

    @Test
    fun `When pin is not created use case will return false`() {
        setUpSharedPrefReturn(null)

        isPinCreatedUseCase.invoke()
            .test()
            .assertValue(false)
    }

    private fun setUpSharedPrefReturn(returnedValue: String?) {
        every {
            encryptedSharedPrefManager.readString(key = PIN_KEY, defValue = null)
        } returns returnedValue
    }

    private companion object {
        const val PIN = "1234"
    }
}