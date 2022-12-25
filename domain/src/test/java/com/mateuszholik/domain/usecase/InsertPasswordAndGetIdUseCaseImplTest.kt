package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.mappers.NewPasswordMapper
import com.mateuszholik.domain.models.NewPassword
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.Test
import com.mateuszholik.data.repositories.models.NewPassword as DataNewPassword

internal class InsertPasswordAndGetIdUseCaseImplTest {

    private val newPasswordMapper = mockk<NewPasswordMapper>()
    private val passwordsRepository = mockk<PasswordsRepository>()

    private val insertPasswordAndGetIdUseCase = InsertPasswordAndGetIdUseCaseImpl(
        passwordsRepository = passwordsRepository,
        newPasswordMapper = newPasswordMapper
    )

    @Test
    fun `When repository correctly inserted and returned id then use case will return this id`() {
        every {
            newPasswordMapper.map(NEW_PASSWORD)
        } returns DATA_NEW_PASSWORD

        every {
            passwordsRepository.insertAndGetId(DATA_NEW_PASSWORD)
        } returns Single.just(ID)

        insertPasswordAndGetIdUseCase(NEW_PASSWORD)
            .test()
            .assertValue(ID)
    }

    private companion object {
        const val ID = 1L
        const val PASSWORD = "password"
        const val PLATFORM_NAME = "platform"
        val NEW_PASSWORD = NewPassword(
            password = PASSWORD,
            platformName = PLATFORM_NAME
        )
        val DATA_NEW_PASSWORD = DataNewPassword(
            password = PASSWORD,
            platformName = PLATFORM_NAME
        )
    }
}