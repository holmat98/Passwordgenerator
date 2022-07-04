package com.mateuszholik.domain.validators

import com.mateuszholik.domain.constants.PasswordConstants.MIN_PASSWORD_LENGTH

internal interface PasswordLengthValidator : BaseValidator<String>

internal class PasswordLengthValidatorImpl: PasswordLengthValidator {

    override fun validate(param: String): Boolean = param.length >= MIN_PASSWORD_LENGTH
}