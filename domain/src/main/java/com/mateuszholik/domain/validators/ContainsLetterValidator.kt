package com.mateuszholik.domain.validators

internal interface ContainsLetterValidator : BaseValidator<String>

internal class ContainsLetterValidatorImpl : ContainsLetterValidator {

    override fun validate(param: String): Boolean = LETTER_REGEX.containsMatchIn(param)

    private companion object {
        val LETTER_REGEX = """.*[a-z].*""".toRegex()
    }
}