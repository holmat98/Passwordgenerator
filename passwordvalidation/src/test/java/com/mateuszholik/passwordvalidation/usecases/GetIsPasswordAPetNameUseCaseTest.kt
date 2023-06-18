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
                firstResult = true,
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondResult = true,
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdResult = true,
                expected = true
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstResult = true,
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondResult = true,
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdResult = false,
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstResult = true,
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondResult = false,
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdResult = true,
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstResult = true,
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondResult = false,
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdResult = false,
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstResult = false,
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondResult = true,
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdResult = true,
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstResult = false,
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondResult = true,
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdResult = false,
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstResult = false,
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondResult = false,
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdResult = true,
                expected = false
            ),
            DatabaseBasedValidationTestCase(
                testedValue = CHECKED_NAME,
                firstResult = false,
                secondString = CHECKED_NAME_WITHOUT_LEET_SPEAK,
                secondResult = false,
                thirdString = CHECKED_NAME_WITHOUT_NUMBERS,
                thirdResult = false,
                expected = false
            ),
        ).map { testCase ->
            DynamicTest.dynamicTest(
                "When matching pet names from database for " +
                        "(same text) ${testCase.testedValue} are ${testCase.firstResult}, " +
                        "(leet speek removed) ${testCase.secondString} are ${testCase.secondResult} " +
                        "and (numbers removed) ${testCase.thirdString} are ${testCase.thirdResult} " +
                        "for given ${testCase.testedValue} then result is ${testCase.expected}"
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
        hasMatchingWords: Boolean,
        textWithoutNumbers: String,
        hasMatchingWordsForTextWithoutNumbers: Boolean,
        textWithoutLeetSpeek: String,
        hasMatchingWordsForTextWithoutLeetSpeek: Boolean
    ) {
        every {
            commonPetsNameDao.getMatchingPetNames(testedValue)
        } returns Single.just(hasMatchingWords)
        every {
            commonPetsNameDao.getMatchingPetNames(textWithoutNumbers)
        } returns Single.just(hasMatchingWordsForTextWithoutNumbers)
        every {
            commonPetsNameDao.getMatchingPetNames(textWithoutLeetSpeek)
        } returns Single.just(hasMatchingWordsForTextWithoutLeetSpeek)
    }


    private companion object {
        const val CHECKED_NAME = "F@f1k"
        const val CHECKED_NAME_WITHOUT_LEET_SPEAK = "Fafik"
        const val CHECKED_NAME_WITHOUT_NUMBERS = "F@fk"
    }
}
