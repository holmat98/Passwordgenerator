package com.mateuszholik.passwordvalidation.usecases

import com.mateuszholik.passwordvalidation.db.daos.CommonPetsNameDao
import com.mateuszholik.passwordvalidation.models.DatabaseBasedValidationTestCase
import com.mateuszholik.passwordvalidation.transformers.StringTransformer
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class GetIsPasswordAPetNameUseCaseTest {

    private val commonPetsNameDao = mockk<CommonPetsNameDao>()
    private val stringTransformer = mockk<StringTransformer>()

    private val getIsPasswordAPetNameUseCase = GetIsPasswordAPetNameUseCase(
        commonPetsNameDao = commonPetsNameDao,
        stringTransformer = stringTransformer
    )

    @TestFactory
    fun checkCommonWords() =
        listOf(
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstReturn = emptyList(),
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondReturn = emptyList(),
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdReturn = emptyList(),
                expected = true
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstReturn = emptyList(),
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondReturn = emptyList(),
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdReturn = listOf(MATCHING_NAME_WITHOUT_NUMBERS),
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstReturn = emptyList(),
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondReturn = listOf(MATCHING_NAME_WITHOUT_LEET_SPEAK),
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdReturn = emptyList(),
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstReturn = emptyList(),
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondReturn = listOf(MATCHING_NAME_WITHOUT_LEET_SPEAK),
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdReturn = listOf(MATCHING_NAME_WITHOUT_NUMBERS),
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstReturn = listOf(MATCHING_NAME),
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondReturn = emptyList(),
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdReturn = emptyList(),
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstReturn = listOf(MATCHING_NAME),
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondReturn = emptyList(),
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdReturn = listOf(MATCHING_NAME_WITHOUT_NUMBERS),
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstReturn = listOf(MATCHING_NAME),
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondReturn = listOf(MATCHING_NAME_WITHOUT_LEET_SPEAK),
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdReturn = emptyList(),
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstReturn = listOf(MATCHING_NAME),
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondReturn = listOf(MATCHING_NAME_WITHOUT_LEET_SPEAK),
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdReturn = listOf(MATCHING_NAME_WITHOUT_NUMBERS),
                expected = false
            ),
        ).map { testCase ->
            DynamicTest.dynamicTest(
                "When matching pet names from database for " +
                        "(same text) ${testCase.testedValue} are ${testCase.firstReturn}, " +
                        "(leet speek removed) ${testCase.secondString} are ${testCase.secondReturn} " +
                        "and (numbers removed) ${testCase.thirdString} are ${testCase.thirdReturn} " +
                        "for given ${testCase.testedValue} then result is ${testCase.expected}"
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

                getIsPasswordAPetNameUseCase(testCase.testedValue)
                    .test()
                    .assertValue(testCase.expected)
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
            commonPetsNameDao.getMatchingPetNames(testedValue)
        } returns Single.just(matchingWords)
        every {
            commonPetsNameDao.getMatchingPetNames(textWithoutNumbers)
        } returns Single.just(matchingWordsForTextWithoutNumbers)
        every {
            commonPetsNameDao.getMatchingPetNames(textWithoutLeetSpeek)
        } returns Single.just(matchingWordsForTextWithoutLeetSpeek)
    }


    private companion object {
        const val CHECKED_NAME = "F@f1k"
        const val CHECKED_NAME_WITHOUT_LEET_SPEAK = "Fafik"
        const val CHECKED_NAME_WITHOUT_NUMBERS = "F@fk"
        const val MATCHING_NAME = "F@f1k"
        const val MATCHING_NAME_WITHOUT_LEET_SPEAK = "Fafik"
        const val MATCHING_NAME_WITHOUT_NUMBERS = "F@fk"
    }
}