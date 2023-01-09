package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.KeyboardCharactersData
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.KEYBOARD_PATTERN
import com.mateuszholik.passwordvalidation.utils.Numbers.INVALID_DISTANCE
import com.mateuszholik.passwordvalidation.utils.Numbers.INVALID_SHORTER_DISTANCE
import com.mateuszholik.passwordvalidation.utils.Numbers.MAX_NUM_OF_PATTERNS
import com.mateuszholik.passwordvalidation.utils.Numbers.NONE
import io.reactivex.rxjava3.core.Single
import kotlin.math.abs

internal class KeyboardPatternsValidationStrategyImpl : PasswordValidationStrategy {

    override fun validate(password: String): Single<PasswordValidationResult> =
        Single.fromCallable {
            var patternsCounter = 0

            for (index in 0 until password.length - 1) {
                val firstCharacterData =
                    KEYBOARD_CHARACTERS_DATA.find { it.char == password[index] }
                val secondCharacterData =
                    KEYBOARD_CHARACTERS_DATA.find { it.char == password[index + 1] }

                if (firstCharacterData != null && secondCharacterData != null) {
                    if (abs(firstCharacterData.column - secondCharacterData.column) in INVALID_DISTANCE
                        && abs(firstCharacterData.row - secondCharacterData.row) in INVALID_SHORTER_DISTANCE
                    ) {
                        patternsCounter += 1
                    }
                }
            }

            PasswordValidationResult(
                validationType = KEYBOARD_PATTERN,
                validationResult = patternsCounter <= MAX_NUM_OF_PATTERNS,
                score = getScore(patternsCounter),
                maxScore = KEYBOARD_PATTERN.maxScore
            )
        }

    private fun getScore(patternsCounter: Int): Int =
        when (patternsCounter) {
            NONE -> KEYBOARD_PATTERN.maxScore
            MAX_NUM_OF_PATTERNS -> KEYBOARD_PATTERN.maxScore / 2
            else -> NONE
        }


    private companion object {
        val KEYBOARD_CHARACTERS_DATA = listOf(
            KeyboardCharactersData(row = 0, column = 0, '1'),
            KeyboardCharactersData(row = 0, column = 0, '!'),
            KeyboardCharactersData(row = 0, column = 1, '2'),
            KeyboardCharactersData(row = 0, column = 1, '@'),
            KeyboardCharactersData(row = 0, column = 2, '3'),
            KeyboardCharactersData(row = 0, column = 2, '#'),
            KeyboardCharactersData(row = 0, column = 3, '4'),
            KeyboardCharactersData(row = 0, column = 3, '$'),
            KeyboardCharactersData(row = 0, column = 4, '5'),
            KeyboardCharactersData(row = 0, column = 4, '%'),
            KeyboardCharactersData(row = 0, column = 5, '6'),
            KeyboardCharactersData(row = 0, column = 5, '^'),
            KeyboardCharactersData(row = 0, column = 6, '7'),
            KeyboardCharactersData(row = 0, column = 6, '&'),
            KeyboardCharactersData(row = 0, column = 7, '8'),
            KeyboardCharactersData(row = 0, column = 7, '*'),
            KeyboardCharactersData(row = 0, column = 8, '9'),
            KeyboardCharactersData(row = 0, column = 8, '('),
            KeyboardCharactersData(row = 0, column = 9, '0'),
            KeyboardCharactersData(row = 0, column = 9, ')'),
            KeyboardCharactersData(row = 0, column = 10, '-'),
            KeyboardCharactersData(row = 0, column = 10, '_'),
            KeyboardCharactersData(row = 0, column = 11, '+'),
            KeyboardCharactersData(row = 0, column = 11, '='),
            KeyboardCharactersData(row = 1, column = 0, 'q'),
            KeyboardCharactersData(row = 1, column = 1, 'w'),
            KeyboardCharactersData(row = 1, column = 2, 'e'),
            KeyboardCharactersData(row = 1, column = 3, 'r'),
            KeyboardCharactersData(row = 1, column = 4, 't'),
            KeyboardCharactersData(row = 1, column = 5, 'y'),
            KeyboardCharactersData(row = 1, column = 6, 'u'),
            KeyboardCharactersData(row = 1, column = 7, 'i'),
            KeyboardCharactersData(row = 1, column = 8, 'o'),
            KeyboardCharactersData(row = 1, column = 9, 'p'),
            KeyboardCharactersData(row = 1, column = 10, '['),
            KeyboardCharactersData(row = 1, column = 10, '{'),
            KeyboardCharactersData(row = 1, column = 11, ']'),
            KeyboardCharactersData(row = 1, column = 11, '}'),
            KeyboardCharactersData(row = 1, column = 12, '\\'),
            KeyboardCharactersData(row = 1, column = 12, '|'),
            KeyboardCharactersData(row = 2, column = 0, 'a'),
            KeyboardCharactersData(row = 2, column = 1, 's'),
            KeyboardCharactersData(row = 2, column = 2, 'd'),
            KeyboardCharactersData(row = 2, column = 3, 'f'),
            KeyboardCharactersData(row = 2, column = 4, 'g'),
            KeyboardCharactersData(row = 2, column = 5, 'h'),
            KeyboardCharactersData(row = 2, column = 6, 'j'),
            KeyboardCharactersData(row = 2, column = 7, 'k'),
            KeyboardCharactersData(row = 2, column = 8, 'l'),
            KeyboardCharactersData(row = 2, column = 9, ';'),
            KeyboardCharactersData(row = 2, column = 9, ':'),
            KeyboardCharactersData(row = 3, column = 0, 'z'),
            KeyboardCharactersData(row = 3, column = 1, 'x'),
            KeyboardCharactersData(row = 3, column = 2, 'c'),
            KeyboardCharactersData(row = 3, column = 3, 'v'),
            KeyboardCharactersData(row = 3, column = 4, 'b'),
            KeyboardCharactersData(row = 3, column = 5, 'n'),
            KeyboardCharactersData(row = 3, column = 6, 'm'),
            KeyboardCharactersData(row = 3, column = 7, ','),
            KeyboardCharactersData(row = 3, column = 7, '<'),
            KeyboardCharactersData(row = 3, column = 8, '.'),
            KeyboardCharactersData(row = 3, column = 8, '>'),
            KeyboardCharactersData(row = 3, column = 9, '/'),
            KeyboardCharactersData(row = 3, column = 9, '?'),
        )
    }
}