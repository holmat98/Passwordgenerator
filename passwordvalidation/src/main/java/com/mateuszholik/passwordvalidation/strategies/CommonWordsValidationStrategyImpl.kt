package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.db.daos.CommonWordDao
import com.mateuszholik.passwordvalidation.extensions.removeLeetText
import com.mateuszholik.passwordvalidation.extensions.removeNumbers
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.COMMON_WORD
import com.mateuszholik.passwordvalidation.utils.Numbers.NONE
import io.reactivex.rxjava3.core.Single

internal class CommonWordsValidationStrategyImpl(
    private val commonWordDao: CommonWordDao
) : PasswordValidationStrategy {

    override fun validate(password: String): Single<PasswordValidationResult> =
        Single.zip(
            commonWordDao.getMatchingWords(password),
            commonWordDao.getMatchingWords(password.removeLeetText()),
            commonWordDao.getMatchingWords(password.removeNumbers()),
            commonWordDao.getMatchingWords(password.removeNumbers().removeLeetText())
        ) { matchingWordsWithPassword,
            matchingWordsWithEditedPassword,
            matchingWordsWithoutNumbers,
            matchingWordsWithoutNumbersAndLeetText ->
            val result = matchingWordsWithPassword.isEmpty()
                    && matchingWordsWithEditedPassword.isEmpty()
                    && matchingWordsWithoutNumbers.isEmpty()
                    && matchingWordsWithoutNumbersAndLeetText.isEmpty()

            PasswordValidationResult(
                validationType = COMMON_WORD,
                validationResult = result,
                score = if (result) COMMON_WORD.maxScore else NONE,
                maxScore = COMMON_WORD.maxScore
            )
        }
}