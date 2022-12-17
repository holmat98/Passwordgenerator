package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class SavePinUseCaseImplTest {

    private val encryptedSharedPrefManager = mockk<EncryptedSharedPrefManager>() {
        every { write(any(), "1234") } returns Unit
    }
    private val createPinUseCase = SavePinUseCaseImpl(encryptedSharedPrefManager)

    @Test
    fun `When pin is correct then it is saved to shared preferences`() {
        createPinUseCase("1234")
            .test()
            .assertComplete()
    }

    private companion object {
        val THROWABLE_ERROR = Throwable("Wrong pin length")
    }
}