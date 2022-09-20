package com.mateuszholik.passwordvalidation.models

data class PasswordValidationResult(
    val validationType: PasswordValidationType,
    val passedValidation: Boolean,
    val score: Int,
    val maxScore: Int
)
