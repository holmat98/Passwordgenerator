package com.mateuszholik.passwordvalidation.models

enum class PasswordValidationType(internal val maxScore: Int) {
    SMALL_LETTERS(5),
    UPPERCASE_LETTERS(5),
    SPECIAL_CHARACTERS(5),
    NUMBERS(5),
    LENGTH(10),
    COMMON_PASSWORD(10),
    COMMON_WORD(10),
    COMMON_NAME(10)
}