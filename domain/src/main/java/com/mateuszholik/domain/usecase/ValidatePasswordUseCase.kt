package com.mateuszholik.domain.usecase

import com.mateuszholik.domain.managers.PasswordScoreManager
import com.mateuszholik.domain.models.PasswordScore
import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import com.mateuszholik.domain.validators.*
import io.reactivex.rxjava3.core.Single

interface ValidatePasswordUseCase : ParameterizedSingleUseCase<String, PasswordScore>

internal class ValidatePasswordUseCaseImpl(
    private val passwordScoreManager: PasswordScoreManager,
    private val containsLetterValidator: ContainsLetterValidator,
    private val containsUpperCaseValidator: ContainsUpperCaseValidator,
    private val containsNumberValidator: ContainsNumberValidator,
    private val containsSpecialCharacterValidator: ContainsSpecialCharacterValidator,
    private val passwordLengthValidator: PasswordLengthValidator
) : ValidatePasswordUseCase {

    override fun invoke(param: String): Single<PasswordScore> =
        Single.just(
            PasswordScore(
                score = passwordScoreManager.getScore(param),
                containsLetters = containsLetterValidator.validate(param),
                containsUpperCaseLetters = containsUpperCaseValidator.validate(param),
                containsNumbers = containsNumberValidator.validate(param),
                containsSpecialCharacters = containsSpecialCharacterValidator.validate(param),
                hasMinimumLength = passwordLengthValidator.validate(param)
            )
        )
}