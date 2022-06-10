package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class CreatePinUseCaseImplTest {

    private val encryptedSharedPrefManager = mockk<EncryptedSharedPrefManager>()
    private val createPinUseCase = CreatePinUseCaseImpl(encryptedSharedPrefManager)


    @Test
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
            .assertResult(encryptedSharedPrefManager.write(any(), "1234"))
    }

    private companion object {
        val THROWABLE_ERROR = Throwable("Wrong pin length")
    }
}