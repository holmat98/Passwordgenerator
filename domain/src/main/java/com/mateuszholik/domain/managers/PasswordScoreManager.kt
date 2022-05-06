package com.mateuszholik.domain.managers

import com.mateuszholik.domain.constants.PasswordConstants.MIN_PASSWORD_LENGTH
import com.mateuszholik.domain.validators.ContainsLetterValidator
import com.mateuszholik.domain.validators.ContainsNumberValidator
import com.mateuszholik.domain.validators.ContainsSpecialCharacterValidator
import com.mateuszholik.domain.validators.ContainsUpperCaseValidator
import kotlin.math.abs

interface PasswordScoreManager {

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
                    addPointsForSpecialCharacters(password) +
                    addPointsForNotContainingPatterns(password)
        val length = password.length

        return (((factor * length) / (MAX_PASSWORD_FACTOR * MAX_PASSWORD_LENGTH)) * 100).toInt()
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

    private fun addPointsForNotContainingPatterns(password: String): Double {
        var numOfPatterns = 0
        for (i in 0..password.length - 2) {
            val nextCharsDiff =
                abs(password[i].lowercaseChar().code - password[i + 1].lowercaseChar().code)
            if (password[i].lowercaseChar().code == password[i + 1].lowercaseChar().code || nextCharsDiff in 0..2) {
                numOfPatterns++
            }
        }

        return when (numOfPatterns) {
            0 -> {
                if (password.length >= MIN_PASSWORD_LENGTH) TEN_POINTS else FIVE_POINTS
            }
            1 -> {
                if (password.length >= MIN_PASSWORD_LENGTH) SEVEN_AND_HALF_POINTS else TWO_AND_HALF_POINTS
            }
            2 -> {
                if (password.length >= MIN_PASSWORD_LENGTH) FIVE_POINTS else ZERO_POINTS
            }
            else -> ZERO_POINTS
        }
    }

    private companion object {
        const val ZERO_POINTS = 0.0
        const val HALF_POINTS = 0.5
        const val ONE_POINT = 1.0
        const val TWO_POINTS = 2.0
        const val TWO_AND_HALF_POINTS = 2.5
        const val FIVE_POINTS = 5.0
        const val SEVEN_AND_HALF_POINTS = 7.5
        const val TEN_POINTS = 10.0
        const val MAX_PASSWORD_LENGTH: Double = 20.0
        const val MAX_PASSWORD_FACTOR: Double = 20.0
    }
}