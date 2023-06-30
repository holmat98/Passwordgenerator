package com.mateuszholik.passwordvalidation.models

enum class PasswordValidationType(internal val maxScore: Int) {
    SMALL_LETTERS(30),
    UPPERCASE_LETTERS(30),
    SPECIAL_CHARACTERS(30),
    NUMBERS(30),
    LENGTH(60),
    COMMON_PASSWORD(20),
    COMMON_WORD(10),
    COMMON_NAME(10),
    ALPHABETICAL_PATTERN(10),
    KEYBOARD_PATTERN(10)
}
