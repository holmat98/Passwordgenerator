package com.mateuszholik.domain.factories

import com.mateuszholik.domain.constants.Constants.EMPTY_STRING
import com.mateuszholik.domain.extensions.getRandom
import com.mateuszholik.domain.extensions.getRandomAndRemove
import com.mateuszholik.domain.models.PasswordCharacterType
import kotlin.random.Random

interface PasswordFactory {

    fun create(length: Int): String
}

internal class PasswordFactoryImpl : PasswordFactory {

    override fun create(length: Int): String {
        val availableIndexes = (0 until length).toMutableList()
        val result = MutableList(length) { EMPTY_STRING }
        val numberOfCharactersByType = getNumberOfCharacterByType(length)

        numberOfCharactersByType.forEach { (passwordCharacterType, numOfCharacters) ->
            repeat(numOfCharacters) {
                val index = availableIndexes.getRandomAndRemove()
                PASSWORD_CHARACTERS[passwordCharacterType]?.let { characters ->
                    result[index] = characters.getRandom()
                }
            }
        }

        return result.joinToString(EMPTY_STRING)
    }

    private fun getNumberOfCharacterByType(passwordLength: Int): Map<PasswordCharacterType, Int> {
        var numOfCharacterTypesLeft = PasswordCharacterType.values().size
        val result = mutableMapOf<PasswordCharacterType, Int>()

        PasswordCharacterType.values().forEach { characterType ->
            numOfCharacterTypesLeft--

            val maxNumOfCharactersForCurrentType = when (numOfCharacterTypesLeft) {
                PasswordCharacterType.values().size - 1 -> passwordLength / 2
                else -> passwordLength - result.values.sum() - numOfCharacterTypesLeft
            }

            val amountOfCharacters: Int = if (numOfCharacterTypesLeft > 1) {
                    Random.nextInt(maxNumOfCharactersForCurrentType) + 1 // [1;maxNumOfCharactersForCurrentType]
                } else {
                    maxNumOfCharactersForCurrentType
                }

            result[characterType] = amountOfCharacters
        }

        return result
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