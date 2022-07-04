package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class CreatePinUseCaseImplTest {

    private val encryptedSharedPrefManager = mockk<EncryptedSharedPrefManager>() {
        every { write(any(), "1234") } returns Unit
    }
    private val createPinUseCase = CreatePinUseCaseImpl(encryptedSharedPrefManager)

    @Test(expected = Throwable::class)
    fun `When pin is to short then error is returned`() {
        createPinUseCase("123")
            .test()
            .assertError(THROWABLE_ERROR)
    }

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