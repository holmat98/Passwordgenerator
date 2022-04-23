package com.mateuszholik.domain.managers

import com.mateuszholik.domain.constants.PasswordConstants.MIN_PASSWORD_LENGTH
import com.mateuszholik.domain.validators.ContainsLetterValidator
import com.mateuszholik.domain.validators.ContainsNumberValidator
import com.mateuszholik.domain.validators.ContainsSpecialCharacterValidator
import com.mateuszholik.domain.validators.ContainsUpperCaseValidator

internal interface PasswordScoreManager {

    fun getScore(password: String): Int
}

internal class PasswordScoreManagerImpl(
    private val containsLetterValidator: ContainsLetterValidator,
    private val containsUpperCaseValidator: ContainsUpperCaseValidator,
    private val containsNumberValidator: ContainsNumberValidator,
    private val containsSpecialCharacterValidator: ContainsSpecialCharacterValidator
) : PasswordScoreManager {

    override fun getScore(password: String): Int {
        val factor =
            addPointsForLetters(password) +
            addPointsForNumbers(password) +
            addPointsForUppercaseLetters(password) +
            addPointsForSpecialCharacters(password)
        val length = password.length

        return (((factor * length) / (MAX_PASSWORD_FACTOR * MAX_PASSWORD_LENGTH))*100).toInt()
    }

    private fun addPointsForLetters(password: String) =
        when {
            containsLetterValidator.validate(password) && password.length >= MIN_PASSWORD_LENGTH -> TWO_POINTS
            containsLetterValidator.validate(password) && password.length < MIN_PASSWORD_LENGTH -> ONE_POINT
            else -> ZERO_POINTS
        }

    private fun addPointsForUppercaseLetters(password: String) =
        when {
            containsUpperCaseValidator.validate(password) && password.length >= MIN_PASSWORD_LENGTH -> TWO_POINTS
            containsUpperCaseValidator.validate(password) && password.length < MIN_PASSWORD_LENGTH -> ONE_POINT
            else -> ZERO_POINTS
        }

    private fun addPointsForNumbers(password: String) =
        when {
            containsNumberValidator.validate(password) && password.length >= MIN_PASSWORD_LENGTH -> ONE_POINT
            containsNumberValidator.validate(password) && password.length < MIN_PASSWORD_LENGTH -> HALF_POINTS
            else -> ZERO_POINTS
        }

    private fun addPointsForSpecialCharacters(password: String) =
        when {
            containsSpecialCharacterValidator.validate(password) && password.length >= MIN_PASSWORD_LENGTH -> FIVE_POINTS
            containsSpecialCharacterValidator.validate(password) && password.length < MIN_PASSWORD_LENGTH -> TWO_AND_HALF_POINTS
            else -> ZERO_POINTS
        }

    private companion object {
        const val ZERO_POINTS = 0.0
        const val HALF_POINTS = 0.5
        const val ONE_POINT = 1.0
        const val TWO_POINTS = 2.0
        const val TWO_AND_HALF_POINTS = 2.5
        const val FIVE_POINTS = 5.0
        const val MAX_PASSWORD_LENGTH: Double = 20.0
        const val MAX_PASSWORD_FACTOR: Double = 20.0
    }
}