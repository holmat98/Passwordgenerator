package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.db.daos.CommonNameDao
import com.mateuszholik.passwordvalidation.db.daos.CommonPetsNameDao
import com.mateuszholik.passwordvalidation.extensions.removeLeetText
import com.mateuszholik.passwordvalidation.extensions.removeNumbers
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
            commonNameDao.getMatchingNames(password.removeNumbers()),
            commonNameDao.getMatchingNames(password.removeNumbers().removeLeetText()),
            commonPetsNameDao.getMatchingPetNames(password),
            commonPetsNameDao.getMatchingPetNames(password.removeLeetText()),
            commonPetsNameDao.getMatchingPetNames(password.removeNumbers()),
            commonPetsNameDao.getMatchingPetNames(password.removeNumbers().removeLeetText())
        ) { matchingNamesWithPassword,
            matchingNamesWithEditedPassword,
            matchingNamesWithoutNumbers,
            matchingNamesWithoutNumbersAndLeet,
            matchingPetNamesWithPassword,
            matchingPetNamesWithEditedPassword,
            matchingPetNamesWithoutNumbers,
            matchingPetNamesWithoutNumbersAndLeet ->
            val result = matchingNamesWithPassword.isEmpty()
                    && matchingNamesWithEditedPassword.isEmpty()
                    && matchingNamesWithoutNumbers.isEmpty()
                    && matchingNamesWithoutNumbersAndLeet.isEmpty()
                    && matchingPetNamesWithPassword.isEmpty()
                    && matchingPetNamesWithEditedPassword.isEmpty()
                    && matchingPetNamesWithoutNumbers.isEmpty()
                    && matchingPetNamesWithoutNumbersAndLeet.isEmpty()

            PasswordValidationResult(
                validationType = COMMON_NAME,
                validationResult = result,
                score = if (result) COMMON_NAME.maxScore else Numbers.NONE,
                maxScore = COMMON_NAME.maxScore
            )
        }
}