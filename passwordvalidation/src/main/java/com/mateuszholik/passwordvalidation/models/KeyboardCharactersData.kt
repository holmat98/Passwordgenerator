package com.mateuszholik.passwordvalidation.models

internal data class KeyboardCharactersData(
    val row: Int,
    val column: Int,
    val char: Char
)