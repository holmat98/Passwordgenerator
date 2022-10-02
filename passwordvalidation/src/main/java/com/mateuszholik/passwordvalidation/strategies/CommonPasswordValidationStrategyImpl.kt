package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.db.daos.CommonPasswordDao
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.COMMON_PASSWORD
import com.mateuszholik.passwordvalidation.utils.Numbers.NONE
import io.reactivex.rxjava3.core.Single

internal class CommonPasswordValidationStrategyImpl(
    private val commonPasswordDao: CommonPasswordDao
) : PasswordValidationStrategy {

    override fun validate(password: String): Single<PasswordValidationResult> =
        commonPasswordDao.getMatchingPasswords(password).map { matchingPasswords ->
            val result = matchingPasswords.isEmpty()

            PasswordValidationResult(
                validationType = COMMON_PASSWORD,
                validationResult = result,
                score = if (result) COMMON_PASSWORD.maxScore else NONE,
                maxScore = COMMON_PASSWORD.maxScore
            )
        }
}