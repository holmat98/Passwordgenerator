package com.mateuszholik.domain.models

data class PasswordScore(
    val containsLetters: Boolean,
    val containsUpperCaseLetters: Boolean,
    val containsNumbers: Boolean,
    val containsSpecialCharacters: Boolean,
    val hasMinimumLength: Boolean
)