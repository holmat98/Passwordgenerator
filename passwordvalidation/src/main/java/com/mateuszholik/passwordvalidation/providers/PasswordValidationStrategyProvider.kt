package com.mateuszholik.passwordvalidation.providers

import com.mateuszholik.passwordvalidation.models.PasswordValidationType
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.*
import com.mateuszholik.passwordvalidation.strategies.*
import com.mateuszholik.passwordvalidation.strategies.NumberValidationStrategyImpl
import com.mateuszholik.passwordvalidation.strategies.PasswordValidationStrategy
import com.mateuszholik.passwordvalidation.strategies.SpecialCharacterValidationStrategyImpl

internal interface PasswordValidationStrategyProvider {

    fun provide(passwordValidationType: PasswordValidationType): PasswordValidationStrategy
}

internal class PasswordValidationStrategyProviderImpl : PasswordValidationStrategyProvider {

    override fun provide(passwordValidationType: PasswordValidationType): PasswordValidationStrategy =
        when (passwordValidationType) {
            SMALL_LETTERS -> SmallLetterValidationStrategyImpl()
            UPPERCASE_LETTERS -> UppercaseLettersValidationStrategyImpl()
            SPECIAL_CHARACTERS -> SpecialCharacterValidationStrategyImpl()
            NUMBERS -> NumberValidationStrategyImpl()
            LENGTH -> LengthValidationStrategyImpl()
        }
}