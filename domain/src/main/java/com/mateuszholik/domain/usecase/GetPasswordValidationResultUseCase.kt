package com.mateuszholik.domain.usecase

import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.usecases.ValidatePasswordUseCase
import io.reactivex.rxjava3.core.Single

interface GetPasswordValidationResultUseCase : ParameterizedSingleUseCase<String, List<PasswordValidationResult>>

internal class GetPasswordValidationResultUseCaseImpl(
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : GetPasswordValidationResultUseCase {

    override fun invoke(param: String): Single<List<PasswordValidationResult>> =
        validatePasswordUseCase(password = param).toList()
}
