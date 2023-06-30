package com.mateuszholik.domain.mappers

import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.models.PasswordInfo
import com.mateuszholik.domain.models.PasswordType
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class PasswordToPasswordTypeMapperImplTest {

    private val passwordToPasswordTypeMapper = PasswordToPasswordTypeMapperImpl()

    @BeforeEach
    fun setUp() {
        mockkStatic(LocalDateTime.now()::class)
        every {
            LocalDateTime.now()
        } returns TODAY
    }

    @AfterEach
    fun tearDown() {
        unmockkStatic(LocalDateTime.now()::class)
    }

    @Test
    fun `When password will expire in 14 days then mapper will return PasswordType ValidPassword`() {
        val testedValue =
            PasswordInfo(PASSWORD.copy(expiringDate = TODAY.plusDays(14)), PASSWORD_SCORE)

        val result = passwordToPasswordTypeMapper.map(testedValue)

        assertThat(result)
            .isInstanceOf(PasswordType.ValidPassword::class.java)
            .isEqualTo(
                PasswordType.ValidPassword(
                    testedValue.password,
                    testedValue.passwordScore
                )
            )
    }

    @Test
    fun `When password will expire after in 5 days then mapper will return PasswordType ExpiringPassword`() {
        val testedValue =
            PasswordInfo(PASSWORD.copy(expiringDate = TODAY.plusDays(5)), PASSWORD_SCORE)

        val result = passwordToPasswordTypeMapper.map(testedValue)

        assertThat(result)
            .isInstanceOf(PasswordType.ExpiringPassword::class.java)
            .isEqualTo(
                PasswordType.ExpiringPassword(
                    testedValue.password,
                    testedValue.passwordScore
                )
            )
    }

    @Test
    fun `When password expired day before then mapper will return PasswordType OutdatedPassword`() {
        val testedValue =
            PasswordInfo(PASSWORD.copy(expiringDate = TODAY.minusDays(1)), PASSWORD_SCORE)

        val result = passwordToPasswordTypeMapper.map(testedValue)

        assertThat(result)
            .isInstanceOf(PasswordType.OutdatedPassword::class.java)
            .isEqualTo(
                PasswordType.OutdatedPassword(
                    testedValue.password,
                    testedValue.passwordScore
                )
            )
    }

    private companion object {
        const val PASSWORD_SCORE = 100
        val TODAY: LocalDateTime = LocalDateTime.of(2022, 12, 26, 12, 0, 0)
        val PASSWORD = Password(
            id = 1L,
            password = "password",
            platformName = "platform",
            expiringDate = TODAY
        )
    }
}
