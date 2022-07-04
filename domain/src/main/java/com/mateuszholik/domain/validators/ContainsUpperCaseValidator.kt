package com.mateuszholik.domain.validators

internal interface ContainsUpperCaseValidator : BaseValidator<String>

internal class ContainsUpperCaseValidatorImpl : ContainsUpperCaseValidator {

    override fun validate(param: String): Boolean = UPPERCASE_REGEX.containsMatchIn(param)

    private companion object {
        val UPPERCASE_REGEX = """.*[A-Z].*""".toRegex()
    }
}