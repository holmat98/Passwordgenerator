package com.mateuszholik.passwordvalidation.usecases

import com.mateuszholik.passwordvalidation.db.daos.CommonNameDao
import com.mateuszholik.passwordvalidation.transformers.StringTransformer
import io.reactivex.rxjava3.core.Single

internal class GetIsPasswordANameUseCase(
    private val commonNameDao: CommonNameDao,
    private val stringTransformer: StringTransformer,
) {
    operator fun invoke(password: String): Single<Boolean> =
        Single.zip(
            commonNameDao.getMatchingNames(password),
            commonNameDao.getMatchingNames(stringTransformer.removeNumbers(password)),
            commonNameDao.getMatchingNames(stringTransformer.removeLeetSpeak(password))
        ) { matchingNames, matchingNamesWithoutNumbers, matchingNamesWithoutLeetSpeek ->

            matchingNames && matchingNamesWithoutNumbers && matchingNamesWithoutLeetSpeek
        }
}
