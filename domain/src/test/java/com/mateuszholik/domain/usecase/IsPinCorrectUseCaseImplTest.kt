package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import com.mateuszholik.data.managers.io.SharedPrefKeys.PIN_KEY
import com.mateuszholik.domain.models.PinState
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class IsPinCorrectUseCaseImplTest {

    private val encryptedSharedPrefManager = mockk<EncryptedSharedPrefManager>()

    private val isPinCorrectUseCaseImpl = IsPinCorrectUseCaseImpl(encryptedSharedPrefManager)

    @Test
    fun `When pin is correct use case will return PinState CORRECT`() {
        setUpSharedPrefReturn(PIN)

        isPinCorrectUseCaseImpl.invoke(PIN)
            .test()
            .assertValue(PinState.CORRECT)
    }

    @Test
    fun `When pin is wrong use case will return PinState Wrong`() {
        setUpSharedPrefReturn(PIN)

        isPinCorrectUseCaseImpl.invoke(WRONG_PIN)
            .test()
            .assertValue(PinState.WRONG)
    }

    @Test
    fun `When there is no saved pin use case will return PinState Wrong`() {
        setUpSharedPrefReturn(null)

        isPinCorrectUseCaseImpl.invoke(PIN)
            .test()
            .assertValue(PinState.WRONG)
    }

    private fun setUpSharedPrefReturn(returnedValue: String?) {
        every {
            encryptedSharedPrefManager.readString(key = PIN_KEY, defValue = null)
        } returns returnedValue
    }

    private companion object {
        const val PIN = "1357"
        const val WRONG_PIN = "1234"
    }

}