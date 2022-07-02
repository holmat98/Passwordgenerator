package com.mateuszholik.domain.models

internal enum class PasswordCharacterType {
    LETTERS,
    CAPITAL_LETTERS,
    SPECIAL_CHARACTERS,
    NUMBERS;

    internal val next: PasswordCharacterType
        get() =
            when(this) {
                LETTERS -> CAPITAL_LETTERS
                CAPITAL_LETTERS -> SPECIAL_CHARACTERS
                SPECIAL_CHARACTERS -> NUMBERS
                NUMBERS -> LETTERS
            }
}