package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails
import com.mateuszholik.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Single

interface GetAutofillPasswordsDetailsUseCase : UseCase<List<AutofillPasswordDetails>>

internal class GetAutofillPasswordsDetailsUseCaseImpl(
    private val passwordsRepository: PasswordsRepository,
) : GetAutofillPasswordsDetailsUseCase {

    override fun invoke(): Single<List<AutofillPasswordDetails>> =
        passwordsRepository.getAutofillPasswordsDetails()
}
