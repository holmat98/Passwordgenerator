package com.mateuszholik.passwordvalidation.models

enum class PasswordValidationType(val maxScore: Int) {
    SMALL_LETTERS(5),
    UPPERCASE_LETTERS(5),
    SPECIAL_CHARACTERS(5),
    NUMBERS(5),
    LENGTH(10)
}