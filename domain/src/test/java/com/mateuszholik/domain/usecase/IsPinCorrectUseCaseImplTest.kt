package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import com.mateuszholik.domain.models.PinState
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class IsPinCorrectUseCaseImplTest {

    private val encryptedSharedPrefManager = mockk<EncryptedSharedPrefManager> {
        every { readString(any()) } returns PIN
    }

    private val isPinCorrectUseCaseImpl = IsPinCorrectUseCaseImpl(encryptedSharedPrefManager)

    @Test
    fun `When pin is correct use case will return PinState CORRECT`() {
        isPinCorrectUseCaseImpl.invoke(PIN)
            .test()
            .assertValue(PinState.CORRECT)
    }

    @Test
    fun `When pin is wrong use case will return PinState Wrong`() {
        isPinCorrectUseCaseImpl.invoke(WRONG_PIN)
            .test()
            .assertValue(PinState.WRONG)
    }

    private companion object {
        const val PIN = ""
        const val WRONG_PIN = "1234"
    }

}