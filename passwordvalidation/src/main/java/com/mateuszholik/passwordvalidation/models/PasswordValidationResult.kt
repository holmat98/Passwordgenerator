package com.mateuszholik.passwordvalidation.models

data class PasswordValidationResult(
    val validationType: PasswordValidationType,
    val validationResult: Boolean,
    val score: Int,
    val maxScore: Int
)
