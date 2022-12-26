package com.mateuszholik.domain.factories

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows

internal class PasswordFactoryImplTest {

    private val passwordFactory = PasswordFactoryImpl()

    @TestFactory
    fun checkPasswordFactory() =
        listOf(8, 10, 12, 20, 30).map { passwordLength ->
            dynamicTest(
                "Password factory will create password of length equal to $passwordLength" +
                        " that contains small, uppercase letters, special characters and numbers"
            ) {
                val result = passwordFactory.create(passwordLength)

                assertThat(result.length).isEqualTo(passwordLength)
                assertThat(SPECIAL_CHARACTER_REGEX.containsMatchIn(result)).isEqualTo(true)
                assertThat(UPPERCASE_REGEX.containsMatchIn(result)).isEqualTo(true)
                assertThat(LETTER_REGEX.containsMatchIn(result)).isEqualTo(true)
                assertThat(NUMBER_REGEX.containsMatchIn(result)).isEqualTo(true)
            }
        }

    @Test
    fun `When password length will be 3 then factory will throw error`() {
        assertThrows<IllegalStateException> {
            passwordFactory.create(3)
        }
    }

    private companion object {
        val SPECIAL_CHARACTER_REGEX = """.*[!@#$'{}%^&*_+=?-].*""".toRegex()
        val UPPERCASE_REGEX = """.*[A-Z].*""".toRegex()
        val LETTER_REGEX = """.*[a-z].*""".toRegex()
        val NUMBER_REGEX = """.*[0-9].*""".toRegex()
    }
}