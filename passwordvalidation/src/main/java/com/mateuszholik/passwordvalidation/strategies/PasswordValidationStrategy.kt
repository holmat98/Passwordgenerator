package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult

internal interface PasswordValidationStrategy {

    fun validate(password: String): PasswordValidationResult
}