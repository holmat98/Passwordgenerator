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
                firstReturn = emptyList(),
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondReturn = emptyList(),
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdReturn = emptyList(),
                expected = true
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstReturn = emptyList(),
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondReturn = emptyList(),
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdReturn = listOf(MATCHING_WORD_WITHOUT_NUMBERS),
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstReturn = emptyList(),
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondReturn = listOf(MATCHING_WORD_WITHOUT_LEET_SPEAK),
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdReturn = emptyList(),
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstReturn = emptyList(),
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondReturn = listOf(MATCHING_WORD_WITHOUT_LEET_SPEAK),
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdReturn = listOf(MATCHING_WORD_WITHOUT_NUMBERS),
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstReturn = listOf(MATCHING_WORD),
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondReturn = emptyList(),
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdReturn = emptyList(),
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstReturn = listOf(MATCHING_WORD),
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondReturn = emptyList(),
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdReturn = listOf(MATCHING_WORD_WITHOUT_NUMBERS),
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstReturn = listOf(MATCHING_WORD),
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondReturn = listOf(MATCHING_WORD_WITHOUT_LEET_SPEAK),
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdReturn = emptyList(),
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_WORD,
                firstReturn = listOf(MATCHING_WORD),
                secondString = CHECKED_WORD_WITHOUT_LEET_SPEAK,
                secondReturn = listOf(MATCHING_WORD_WITHOUT_LEET_SPEAK),
                thirdString = CHECKED_WORD_WITHOUT_NUMBERS,
                thirdReturn = listOf(MATCHING_WORD_WITHOUT_NUMBERS),
                expected = false
            ),
        ).map { testCase ->
            dynamicTest(
                "When matching words from database for " +
                        "(same text) ${testCase.testedValue} are ${testCase.firstReturn}, " +
                        "(leet speek removed) ${testCase.secondString} are ${testCase.secondReturn} " +
                        "and (numbers removed) ${testCase.thirdString} are ${testCase.thirdReturn} " +
                        "for given ${testCase.testedValue} then validation result is ${testCase.expected}"
            ) {
                setStringTransformerDefaultValue(
                    testedValue = testCase.testedValue,
                    leetSpeekReturn = testCase.secondString,
                    numbersReturn = testCase.thirdString
                )
                setDaoReturns(
                    testedValue = testCase.testedValue,
                    matchingWords = testCase.firstReturn,
                    textWithoutNumbers = testCase.thirdString,
                    matchingWordsForTextWithoutNumbers = testCase.thirdReturn,
                    textWithoutLeetSpeek = testCase.secondString,
                    matchingWordsForTextWithoutLeetSpeek = testCase.secondReturn
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
        matchingWords: List<String>,
        textWithoutNumbers: String,
        matchingWordsForTextWithoutNumbers: List<String>,
        textWithoutLeetSpeek: String,
        matchingWordsForTextWithoutLeetSpeek: List<String>
    ) {
        every {
            commonWordDao.getMatchingWords(testedValue)
        } returns Single.just(matchingWords)
        every {
            commonWordDao.getMatchingWords(textWithoutNumbers)
        } returns Single.just(matchingWordsForTextWithoutNumbers)
        every {
            commonWordDao.getMatchingWords(textWithoutLeetSpeek)
        } returns Single.just(matchingWordsForTextWithoutLeetSpeek)
    }


    private companion object {
        const val CHECKED_WORD = "l33tsp34k"
        const val CHECKED_WORD_WITHOUT_LEET_SPEAK = "leetspeak"
        const val CHECKED_WORD_WITHOUT_NUMBERS = "ltspk"
        const val MATCHING_WORD = "l33tsp34k"
        const val MATCHING_WORD_WITHOUT_LEET_SPEAK = "leetspeak"
        const val MATCHING_WORD_WITHOUT_NUMBERS = "ltspk"
    }
}