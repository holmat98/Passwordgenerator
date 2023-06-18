package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.db.daos.CommonWordDao
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.COMMON_WORD
import com.mateuszholik.passwordvalidation.transformers.StringTransformer
import com.mateuszholik.passwordvalidation.utils.Numbers.NONE
import io.reactivex.rxjava3.core.Single

internal class CommonWordsValidationStrategyImpl(
    private val commonWordDao: CommonWordDao,
    private val stringTransformer: StringTransformer,
) : PasswordValidationStrategy {

    override fun validate(password: String): Single<PasswordValidationResult> =
        Single.zip(
            commonWordDao.getMatchingWords(password),
            commonWordDao.getMatchingWords(stringTransformer.removeLeetSpeak(password)),
            commonWordDao.getMatchingWords(stringTransformer.removeNumbers(password)),
        ) { matchingWordsWithPassword, matchingWordsWithEditedPassword, matchingWordsWithoutNumbers ->
            val result =
                matchingWordsWithPassword && matchingWordsWithEditedPassword && matchingWordsWithoutNumbers

            PasswordValidationResult(
                validationType = COMMON_WORD,
                validationResult = result,
                score = if (result) COMMON_WORD.maxScore else NONE,
                maxScore = COMMON_WORD.maxScore
            )
        }
}
