package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.SPECIAL_CHARACTERS
import com.mateuszholik.passwordvalidation.utils.Numbers
import io.reactivex.rxjava3.core.Single

internal class SpecialCharacterValidationStrategyImpl : PasswordValidationStrategy {

    override fun validate(password: String): Single<PasswordValidationResult> =
        Single.just(SPECIAL_CHARACTER_REGEX.containsMatchIn(password))
            .map { hasSpecialCharacter ->
                PasswordValidationResult(
                    validationType = SPECIAL_CHARACTERS,
                    validationResult = hasSpecialCharacter,
                    score = if (hasSpecialCharacter) SPECIAL_CHARACTERS.maxScore else Numbers.NONE,
                    maxScore = SPECIAL_CHARACTERS.maxScore
                )
            }

    private companion object {
        val SPECIAL_CHARACTER_REGEX = """.*[!@#$'{}%^&*_+=?-].*""".toRegex()
    }
}