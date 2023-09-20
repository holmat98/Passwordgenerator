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
    private val getPasswordScoreUseCase = mockk<GetPasswordScoreUseCase>()

    private val insertPasswordAndGetIdUseCase = InsertPasswordAndGetIdUseCaseImpl(
        passwordsRepository = passwordsRepository,
        newPasswordMapper = newPasswordMapper,
        getPasswordScoreUseCase = getPasswordScoreUseCase
    )

    @Test
    fun `When repository correctly inserted and returned id then use case will return this id`() {
        every {
            newPasswordMapper.map(NEW_PASSWORD_PARAM)
        } returns DATA_NEW_PASSWORD

        every {
            passwordsRepository.insertAndGetId(DATA_NEW_PASSWORD)
        } returns Single.just(ID)

        every { getPasswordScoreUseCase(NEW_PASSWORD.password) } returns Single.just(PASSWORD_SCORE)

        insertPasswordAndGetIdUseCase(NEW_PASSWORD)
            .test()
            .assertValue(ID)
    }

    private companion object {
        const val ID = 1L
        const val PASSWORD = "password"
        const val PLATFORM_NAME = "platform"
        const val PASSWORD_SCORE = 50
        val NEW_PASSWORD_PARAM = NewPasswordMapper.Param(
            password = PASSWORD,
            platformName = PLATFORM_NAME,
            website = null,
            isExpiring = false,
            passwordScore = PASSWORD_SCORE,
            packageName = null
        )
        val NEW_PASSWORD = NewPassword(
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            website = null,
            isExpiring = false,
            packageName = null
        )
        val DATA_NEW_PASSWORD = DataNewPassword(
            password = PASSWORD,
            platformName = PLATFORM_NAME,
            website = null,
            isExpiring = false,
            passwordScore = PASSWORD_SCORE,
            packageName = null
        )
    }
}
