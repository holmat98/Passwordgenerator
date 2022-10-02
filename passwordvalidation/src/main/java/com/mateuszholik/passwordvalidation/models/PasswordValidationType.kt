package com.mateuszholik.passwordvalidation.models

enum class PasswordValidationType(internal val maxScore: Int) {
    SMALL_LETTERS(10),
    UPPERCASE_LETTERS(10),
    SPECIAL_CHARACTERS(10),
    NUMBERS(10),
    LENGTH(10),
    COMMON_PASSWORD(10),
    COMMON_WORD(10),
    COMMON_NAME(10),
    ALPHABETICAL_PATTERN(10),
    KEYBOARD_PATTERN(10)
}