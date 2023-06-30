package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.db.daos.CommonWordDao
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.COMMON_WORD
import com.mateuszholik.passwordvalidation.models.DatabaseBasedValidationTestCase
import com.mateuszholik.passwordvalidation.transformers.StringTransformer
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

class CommonWordsValidationStrategyImplTest {

    private val commonWordDao = mockk<CommonWordDao>()
    private val stringTransformer = mockk<StringTransformer>()
    private val commonWordsValidationStrategy = CommonWordsValidationStrategyImpl(
        commonWordDao = commonWordDao,
        stringTransformer = stringTransformer
    )

    @TestFactory
    fun checkCommonWords() =
        listOf(
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstResult = true,
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondResult = true,
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdResult = true,
                expected = true
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstResult = true,
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondResult = true,
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdResult = false,
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstResult = true,
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondResult = false,
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdResult = true,
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstResult = true,
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondResult = false,
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdResult = false,
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstResult = false,
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondResult = true,
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdResult = true,
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstResult = false,
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondResult = true,
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdResult = false,
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstResult = false,
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondResult = false,
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdResult = true,
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstResult = false,
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondResult = false,
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdResult = false,
                expected = false
            ),
        ).map { testCase ->
            dynamicTest(
                "When matching words from database for " +
                        "(same text) ${testCase.testedValue} are ${testCase.firstResult}, " +
                        "(leet speek removed) ${testCase.secondString} are ${testCase.secondResult} " +
                        "and (numbers removed) ${testCase.thirdString} are ${testCase.thirdResult} " +
                        "for given ${testCase.testedValue} then validation result is ${testCase.expected}"
            ) {
                setStringTransformerDefaultValue(
                    testedValue = testCase.testedValue,
                    leetSpeekReturn = testCase.secondString,
                    numbersReturn = testCase.thirdString
                )
                setDaoReturns(
                    testedValue = testCase.testedValue,
                    hasMatchingWords = testCase.firstResult,
                    textWithoutNumbers = testCase.thirdString,
                    hasMatchingWordsForTextWithoutNumbers = testCase.thirdResult,
                    textWithoutLeetSpeek = testCase.secondString,
                    hasMatchingWordsForTextWithoutLeetSpeek = testCase.secondResult
                )

                commonWordsValidationStrategy
                    .validate(testCase.testedValue)
                    .test()
                    .assertValue(
                        PasswordValidationResult(
                            validationType = COMMON_WORD,
                            validationResult = testCase.expected,
                            score = if (testCase.expected) COMMON_WORD.maxScore else 0,
                            maxScore = COMMON_WORD.maxScore
                        )
                    )
            }
        }

    private fun setStringTransformerDefaultValue(
        testedValue: String,
        leetSpeekReturn: String,
        numbersReturn: String
    ) {
        every {
            stringTransformer.removeNumbers(testedValue)
        } returns numbersReturn
        every {
            stringTransformer.removeLeetSpeak(testedValue)
        } returns leetSpeekReturn
    }

    private fun setDaoReturns(
        testedValue: String,
        hasMatchingWords: Boolean,
        textWithoutNumbers: String,
        hasMatchingWordsForTextWithoutNumbers: Boolean,
        textWithoutLeetSpeek: String,
        hasMatchingWordsForTextWithoutLeetSpeek: Boolean
    ) {
        every {
            commonWordDao.getMatchingWords(testedValue)
        } returns Single.just(hasMatchingWords)
        every {
            commonWordDao.getMatchingWords(textWithoutNumbers)
        } returns Single.just(hasMatchingWordsForTextWithoutNumbers)
        every {
            commonWordDao.getMatchingWords(textWithoutLeetSpeek)
        } returns Single.just(hasMatchingWordsForTextWithoutLeetSpeek)
    }


    private companion object {
        const val CHECKED_WORD = "l33tsp34k"
        const val CHECKED_WORD_WITHOUT_LEET_SPEAK = "leetspeak"
        const val CHECKED_WORD_WITHOUT_NUMBERS = "ltspk"
    }
}
