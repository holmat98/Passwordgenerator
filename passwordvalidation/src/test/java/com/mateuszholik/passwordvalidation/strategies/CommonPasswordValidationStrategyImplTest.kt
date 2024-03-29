package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.db.daos.CommonPasswordDao
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.COMMON_PASSWORD
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.Test


class CommonPasswordValidationStrategyImplTest {

    private val commonPasswordDao = mockk<CommonPasswordDao>()
    private val commonWordsValidationStrategy = CommonPasswordValidationStrategyImpl(
        commonPasswordDao = commonPasswordDao
    )

    @Test
    fun `When getMatchingPasswords returns true then the result is true and score is equal to the maxScore`() {
        every {
            commonPasswordDao.getMatchingPasswords(PASSWORD)
        } returns Single.just(true)

        commonWordsValidationStrategy
            .validate(PASSWORD)
            .test()
            .assertValue(
                PasswordValidationResult(
                    validationType = COMMON_PASSWORD,
                    validationResult = true,
                    score = COMMON_PASSWORD.maxScore,
                    maxScore = COMMON_PASSWORD.maxScore
                )
            )
    }

    @Test
    fun `When getMatchingPasswords returns false then the result is false and score is equal to 0`() {
        every {
            commonPasswordDao.getMatchingPasswords(PASSWORD)
        } returns Single.just(false)

        commonWordsValidationStrategy
            .validate(PASSWORD)
            .test()
            .assertValue(
                PasswordValidationResult(
                    validationType = COMMON_PASSWORD,
                    validationResult = false,
                    score = 0,
                    maxScore = COMMON_PASSWORD.maxScore
                )
            )
    }

    private companion object {
        const val PASSWORD = "123456789"
    }
}
