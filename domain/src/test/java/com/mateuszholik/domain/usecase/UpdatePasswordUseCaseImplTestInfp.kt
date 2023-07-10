package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.mappers.UpdatedPasswordMapper
import com.mateuszholik.domain.models.UpdatedPassword
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Completable
import org.junit.jupiter.api.Test
import com.mateuszholik.data.repositories.models.UpdatedPassword as DataUpdatedPassword

internal class UpdatePasswordUseCaseImplTestInfp {

    private val updatedPasswordMapper = mockk<UpdatedPasswordMapper> {
        every { map(UPDATED_PASSWORD) } returns DATA_UPDATED_PASSWORD
    }
    private val passwordsRepository = mockk<PasswordsRepository>()

    private val updatePasswordUseCase = UpdatePasswordUseCaseImpl(
        updatedPasswordMapper = updatedPasswordMapper,
        passwordsRepository = passwordsRepository
    )

    @Test
    fun `When repository successfully updated password then use case also completed successfully`() {
        every { passwordsRepository.update(DATA_UPDATED_PASSWORD) } returns Completable.complete()

        updatePasswordUseCase(UPDATED_PASSWORD)
            .test()
            .assertComplete()
    }

    @Test
    fun `When repository returned error then use case also will return error`() {
        every { passwordsRepository.update(DATA_UPDATED_PASSWORD) } returns Completable.error(ERROR)

        updatePasswordUseCase(UPDATED_PASSWORD)
            .test()
            .assertError(ERROR)
    }

    private companion object {
        val ERROR = Throwable("Error")
        const val ID = 1L
        const val PASSWORD = "password"
        const val PLATFORM = "platform"
        val UPDATED_PASSWORD = UpdatedPassword(
            id = ID,
            platformName = PLATFORM,
            password = PASSWORD
        )
        val DATA_UPDATED_PASSWORD = DataUpdatedPassword(
            id = ID,
            platformName = PLATFORM,
            password = PASSWORD
        )
    }
}
