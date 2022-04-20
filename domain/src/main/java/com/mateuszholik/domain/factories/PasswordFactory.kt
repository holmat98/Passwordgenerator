package com.mateuszholik.domain.factories

import java.lang.StringBuilder
import java.security.SecureRandom

interface PasswordFactory {

    fun create(
        length: Int,
        hasLetters: Boolean = true,
        hasUppercaseLetters: Boolean = true,
        hasNumbers: Boolean = true,
        hasSpecialCharacters: Boolean = true
    ): String
}

internal class PasswordFactoryImpl : PasswordFactory {

    override fun create(
        length: Int,
        hasLetters: Boolean,
        hasUppercaseLetters: Boolean,
        hasNumbers: Boolean,
        hasSpecialCharacters: Boolean
    ): String {
        var allCharacters = ""

        if (hasLetters) { allCharacters += LETTERS }
        if (hasUppercaseLetters) { allCharacters += UPPERCASE_LETTERS }
        if (hasNumbers) { allCharacters += NUMBERS }
        if (hasSpecialCharacters) { allCharacters += SPECIAL_CHARACTERS }

        val secureRandom = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM)
        val password = StringBuilder()
        for (i in 0..length) {
            val index = secureRandom.nextInt(allCharacters.length)
            password.append(allCharacters[index])
        }

        return password.toString()
    }

    private companion object {
        const val LETTERS = "abcdefghijklmnopqrstuvwxyz"
        const val UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQESTUVWXYZ"
        const val NUMBERS = "0123456789"
        const val SPECIAL_CHARACTERS = "!@#$%^&*-_+=?"
        const val SECURE_RANDOM_ALGORITHM = "SHA1PRNG"
    }
}