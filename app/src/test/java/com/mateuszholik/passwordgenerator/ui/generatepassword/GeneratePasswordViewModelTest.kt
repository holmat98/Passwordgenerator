package com.mateuszholik.passwordgenerator.ui.generatepassword

import com.mateuszholik.domain.usecase.CreatePasswordUseCase
import com.mateuszholik.passwordgenerator.extensions.InstantExecutorExtension
import com.mateuszholik.passwordgenerator.extensions.RxSchedulerExtension
import com.mateuszholik.passwordgenerator.extensions.getOrAwaitValue
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.TextProvider
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(value = [RxSchedulerExtension::class, InstantExecutorExtension::class])
internal class GeneratePasswordViewModelTest {

    private val createPasswordUseCase = mockk<CreatePasswordUseCase>()
    private val textProvider = mockk<TextProvider> {
        every { provide(MessageType.PASSWORD_GENERATION_ERROR) } returns ERROR_MSG
    }

    private lateinit var viewModel: GeneratePasswordViewModel

    @BeforeEach
    fun setUp() {
        viewModel = GeneratePasswordViewModel(
            createPasswordUseCase = createPasswordUseCase,
            textProvider = textProvider
        )
    }

    @Test
    fun `When password was properly generated, generatedPassword is updated`() {
        viewModel.passwordLength.value = PASSWORD_LENGTH

        every {
            createPasswordUseCase(PASSWORD_LENGTH)
        } returns Single.just(GENERATED_PASSWORD)

        viewModel.createPassword()

        assertThat(viewModel.generatedPassword.getOrAwaitValue()).isEqualTo(GENERATED_PASSWORD)
    }

    @Test
    fun `When error occurred during password generation, errorOccurred is updated`() {
        viewModel.passwordLength.value = PASSWORD_LENGTH

        every {
            createPasswordUseCase(PASSWORD_LENGTH)
        } returns Single.error(Exception("Error"))

        viewModel.createPassword()

        assertThat(viewModel.errorOccurred.getOrAwaitValue()).isEqualTo(ERROR_MSG)
    }

    private companion object {
        const val PASSWORD_LENGTH = 8
        const val ERROR_MSG = "Error"
        const val GENERATED_PASSWORD = "1234abcd"
    }
}
