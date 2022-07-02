package com.mateuszholik.domain.factories

import com.mateuszholik.domain.constants.Constants
import com.mateuszholik.domain.extensions.getRandom
import com.mateuszholik.domain.extensions.getRandomAndRemove
import com.mateuszholik.domain.models.PasswordCharacterType

interface PasswordFactory {

    fun create(length: Int): String
}

internal class PasswordFactoryImpl : PasswordFactory {

    override fun create(length: Int): String {
        val availableIndexes = (0 until length).toMutableList()
        var currentCharacterType = PasswordCharacterType.values().random()
        val result = MutableList(length) { Constants.EMPTY_STRING }

        while (availableIndexes.isNotEmpty()) {
            val index = availableIndexes.getRandomAndRemove()
            PASSWORD_CHARACTERS[currentCharacterType]?.let {
                result[index] = it.getRandom()
                currentCharacterType = currentCharacterType.nextType
            }
        }

        return result.joinToString(Constants.EMPTY_STRING)
    }

    private companion object {
        val PASSWORD_CHARACTERS = mapOf(
            PasswordCharacterType.LETTERS to "abcdefghijklmnopqrstuvwxyz",
            PasswordCharacterType.UPPERCASE_LETTERS to "ABCDEFGHIJKLMNOPQESTUVWXYZ",
            PasswordCharacterType.SPECIAL_CHARACTERS to "!@#$%^&*-_+=?",
            PasswordCharacterType.NUMBERS to "0123456789"
        )
    }
}