package com.mateuszholik.passwordvalidation.usecases

import com.mateuszholik.passwordvalidation.db.daos.CommonPetsNameDao
import com.mateuszholik.passwordvalidation.transformers.StringTransformer
import io.reactivex.rxjava3.core.Single

internal class GetIsPasswordAPetNameUseCase(
    private val commonPetsNameDao: CommonPetsNameDao,
    private val stringTransformer: StringTransformer
) {
    operator fun invoke(password: String): Single<Boolean> =
        Single.zip(
            commonPetsNameDao.getMatchingPetNames(password),
            commonPetsNameDao.getMatchingPetNames(stringTransformer.removeNumbers(password)),
            commonPetsNameDao.getMatchingPetNames(stringTransformer.removeLeetSpeak(password))
        ) { matchingNames, matchingNamesWithoutNumbers, matchingNamesWithoutLeetSpeek ->

            matchingNames.isEmpty()
                    && matchingNamesWithoutNumbers.isEmpty()
                    && matchingNamesWithoutLeetSpeek.isEmpty()
        }
}