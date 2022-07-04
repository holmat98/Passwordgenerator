package com.mateuszholik.domain.models

internal enum class PasswordCharacterType {
    LETTERS,
    UPPERCASE_LETTERS,
    SPECIAL_CHARACTERS,
    NUMBERS;

    internal val nextType: PasswordCharacterType
        get() =
            when(this) {
                LETTERS -> UPPERCASE_LETTERS
                UPPERCASE_LETTERS -> SPECIAL_CHARACTERS
                SPECIAL_CHARACTERS -> NUMBERS
                NUMBERS -> LETTERS
            }
}