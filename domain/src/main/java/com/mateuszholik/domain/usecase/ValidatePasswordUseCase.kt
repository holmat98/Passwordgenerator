package com.mateuszholik.domain.usecase

import com.mateuszholik.domain.models.PasswordScore
import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import com.mateuszholik.domain.validators.ContainsLetterValidator
import com.mateuszholik.domain.validators.ContainsUpperCaseValidator
import com.mateuszholik.domain.validators.ContainsNumberValidator
import com.mateuszholik.domain.validators.ContainsSpecialCharacterValidator
import com.mateuszholik.domain.validators.PasswordLengthValidator
import io.reactivex.rxjava3.core.Single

interface ValidatePasswordUseCase : ParameterizedSingleUseCase<String, PasswordScore>

internal class ValidatePasswordUseCaseImpl(
    private val containsLetterValidator: ContainsLetterValidator,
    private val containsUpperCaseValidator: ContainsUpperCaseValidator,
    private val containsNumberValidator: ContainsNumberValidator,
    private val containsSpecialCharacterValidator: ContainsSpecialCharacterValidator,
    private val passwordLengthValidator: PasswordLengthValidator
) : ValidatePasswordUseCase {

    override fun invoke(param: String): Single<PasswordScore> =
        Single.zip(
            Single.just(containsLetterValidator.validate(param)),
            Single.just(containsUpperCaseValidator.validate(param)),
            Single.just(containsNumberValidator.validate(param)),
            Single.just(containsSpecialCharacterValidator.validate(param)),
            Single.just(passwordLengthValidator.validate(param))
        ) { containsLetter,
            containsUppercaseLetter,
            containsNumber,
            containsSpecialCharacter,
            hasMinimumLength ->

            PasswordScore(
                containsLetters = containsLetter,
                containsUpperCaseLetters = containsUppercaseLetter,
                containsNumbers = containsNumber,
                containsSpecialCharacters = containsSpecialCharacter,
                hasMinimumLength = hasMinimumLength
            )
        }
}