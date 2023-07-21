package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Completable
import org.junit.jupiter.api.Test

class DeletePasswordUseCaseImplTest {

    private val passwordsRepository = mockk<PasswordsRepository>()

    private val deletePasswordUseCase = DeletePasswordUseCaseImpl(passwordsRepository)

    @Test
    fun `When repository correctly removed password use case will return complete`() {
        every { passwordsRepository.delete(ID) } returns Completable.complete()

        deletePasswordUseCase.invoke(ID)
            .test()
            .assertComplete()
    }

    @Test
    fun `When repository did not removed password use case will return error`() {
        every { passwordsRepository.delete(ID) } returns Completable.error(ERROR)

        deletePasswordUseCase.invoke(ID)
            .test()
            .assertError(ERROR)
    }

    private companion object {
        const val ID = 1L
        val ERROR = Throwable("Error")
    }
}
