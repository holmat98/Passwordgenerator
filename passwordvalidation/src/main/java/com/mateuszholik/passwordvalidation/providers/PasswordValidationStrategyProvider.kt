package com.mateuszholik.passwordvalidation.providers

import com.mateuszholik.passwordvalidation.models.PasswordValidationType
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.*
import com.mateuszholik.passwordvalidation.strategies.PasswordValidationStrategy

internal interface PasswordValidationStrategyProvider {

    fun provide(passwordValidationType: PasswordValidationType): PasswordValidationStrategy
}

internal class PasswordValidationStrategyProviderImpl : PasswordValidationStrategyProvider {

    override fun provide(passwordValidationType: PasswordValidationType): PasswordValidationStrategy =
        when (passwordValidationType) {
            SMALL_LETTERS -> TODO()
            UPPERCASE_LETTERS -> TODO()
            SPECIAL_CHARACTERS -> TODO()
            NUMBERS -> TODO()
            else -> error("Wrong password validation strategy")
        }
}