package com.mateuszholik.domain.usecase

import com.mateuszholik.domain.factories.PasswordFactory
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CreatePasswordUseCaseImplTest {

    private val passwordFactory = mockk<PasswordFactory> {
        every { create(PASSWORD_LENGTH) } returns PASSWORD
    }

    private val createPasswordUseCase = CreatePasswordUseCaseImpl(passwordFactory)

    @Test
    fun `Use case will return single with created password`() {
        createPasswordUseCase.invoke(PASSWORD_LENGTH)
            .test()
            .assertNoErrors()
            .assertValue(PASSWORD)
    }

    private companion object {
        const val PASSWORD_LENGTH = 10
        const val PASSWORD = "Password1234"
    }
}