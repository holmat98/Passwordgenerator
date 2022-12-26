package com.mateuszholik.domain.mappers

import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.models.PasswordType
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class PasswordsListToPasswordsTypeListMapperImplTest {

    private val passwordToPasswordTypeMapper = mockk<PasswordToPasswordTypeMapper> {
        every { map(PASSWORD) } returns PASSWORD_TYPE
        every { map(PASSWORD_2) } returns PASSWORD_TYPE_2
    }

    private val passwordsListToPasswordsTypeListMapper = PasswordsListToPasswordsTypeListMapperImpl(
        passwordToPasswordTypeMapper = passwordToPasswordTypeMapper
    )

    @Test
    fun `When list mapper receive list of passwords it will map it correctly to list of password type models`() {
        val result = passwordsListToPasswordsTypeListMapper.map(listOf(PASSWORD, PASSWORD_2))

        assertThat(result)
            .isEqualTo(listOf(PASSWORD_TYPE, PASSWORD_TYPE_2))
    }

    private companion object {
        val PASSWORD = Password(
            id = 1L,
            password = "password",
            platformName = "platform",
            expiringDate = LocalDateTime.of(2022, 12, 26, 12, 0, 0)
        )
        val PASSWORD_2 = Password(
            id = 2L,
            password = "password2",
            platformName = "platform2",
            expiringDate = LocalDateTime.of(2022, 12, 12, 12, 0, 0)
        )
        val PASSWORD_TYPE = PasswordType.ValidPassword(PASSWORD)
        val PASSWORD_TYPE_2 = PasswordType.OutdatedPassword(PASSWORD_2)
    }
}