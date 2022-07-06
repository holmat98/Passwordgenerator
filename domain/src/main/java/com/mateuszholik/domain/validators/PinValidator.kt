package com.mateuszholik.domain.validators

internal interface PinValidator : BaseValidator<String>

internal class PinValidatorImpl : PinValidator {

    override fun validate(param: String): Boolean = param.length == PIN_CORRECT_LENGTH

    private companion object {
        const val PIN_CORRECT_LENGTH = 4
    }
}