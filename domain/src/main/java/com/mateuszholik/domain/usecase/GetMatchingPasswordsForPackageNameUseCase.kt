package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails
import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import io.reactivex.rxjava3.core.Single

interface GetMatchingPasswordsForPackageNameUseCase :
    ParameterizedSingleUseCase<String?, List<AutofillPasswordDetails>>

internal class GetMatchingPasswordsForPackageNameUseCaseImpl(
    private val passwordsRepository: PasswordsRepository,
) : GetMatchingPasswordsForPackageNameUseCase {

    override fun invoke(param: String?): Single<List<AutofillPasswordDetails>> =
        passwordsRepository.getAutofillPasswordsDetails()
            .map { autofillPasswords ->
                autofillPasswords.filter {
                    it.packageName == param
                }
            }
}
