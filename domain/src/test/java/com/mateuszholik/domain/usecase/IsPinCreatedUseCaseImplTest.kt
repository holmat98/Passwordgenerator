package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.EncryptedSharedPrefManager
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class IsPinCreatedUseCaseImplTest {

    private val encryptedSharedPrefManager = mockk<EncryptedSharedPrefManager>()

    private val isPinCreatedUseCase = IsPinCreatedUseCaseImpl(encryptedSharedPrefManager)

    @Test
    fun `When pin is created use case will return true`() {
        every { encryptedSharedPrefManager.readString(any()) } returns PIN

        isPinCreatedUseCase.invoke()
            .test()
            .assertValue(true)
    }

    @Test
    fun `When pin is not created use case will return false`() {
        every { encryptedSharedPrefManager.readString(any()) } returns ""

        isPinCreatedUseCase.invoke()
            .test()
            .assertValue(false)
    }

    private companion object {
        const val PIN = "1234"
    }
}