package com.mateuszholik.domain.validators

internal interface ContainsNumberValidator : BaseValidator<String>

internal class ContainsNumberValidatorImpl : ContainsNumberValidator {

    override fun validate(param: String): Boolean = NUMBER_REGEX.containsMatchIn(param)

    private companion object {
        val NUMBER_REGEX = """.*[0-9].*""".toRegex()
    }
}