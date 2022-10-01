package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.db.daos.CommonNameDao
import com.mateuszholik.passwordvalidation.db.daos.CommonPetsNameDao
import com.mateuszholik.passwordvalidation.extensions.removeLeetText
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.COMMON_NAME
import com.mateuszholik.passwordvalidation.utils.Numbers
import io.reactivex.rxjava3.core.Single

internal class CommonNameValidationStrategyImpl(
    private val commonNameDao: CommonNameDao,
    private val commonPetsNameDao: CommonPetsNameDao
) : PasswordValidationStrategy {

    override fun validate(password: String): Single<PasswordValidationResult> =
        Single.zip(
            commonNameDao.getMatchingNames(password),
            commonNameDao.getMatchingNames(password.removeLeetText()),
            commonPetsNameDao.getMatchingPetNames(password),
            commonPetsNameDao.getMatchingPetNames(password.removeLeetText())
        ) { matchingNamesWithPassword,
            matchingNamesWithEditedPassword,
            matchingPetNamesWithPassword,
            matchingPetNamesWithEditedPassword ->
            val result = matchingNamesWithPassword.isEmpty()
                    && matchingNamesWithEditedPassword.isEmpty()
                    && matchingPetNamesWithPassword.isEmpty()
                    && matchingPetNamesWithEditedPassword.isEmpty()

            PasswordValidationResult(
                validationType = COMMON_NAME,
                validationResult = result,
                score = if (result) COMMON_NAME.maxScore else Numbers.NONE,
                maxScore = COMMON_NAME.maxScore
            )
        }
}