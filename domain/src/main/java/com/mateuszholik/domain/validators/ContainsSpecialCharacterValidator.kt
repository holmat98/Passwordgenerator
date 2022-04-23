package com.mateuszholik.domain.validators

internal interface ContainsSpecialCharacterValidator : BaseValidator<String>

internal class ContainsSpecialCharacterValidatorImpl : ContainsSpecialCharacterValidator {

    override fun validate(param: String): Boolean = SPECIAL_CHARACTER_REGEX.containsMatchIn(param)

    private companion object {
        val SPECIAL_CHARACTER_REGEX = """.*[!@#$'{}%^&*-_+=?].*""".toRegex()
    }
}