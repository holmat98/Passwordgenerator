package com.mateuszholik.passwordvalidation.usecases

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType
import com.mateuszholik.passwordvalidation.providers.PasswordValidationStrategyProvider
import io.reactivex.rxjava3.core.Observable

interface ValidatePasswordUseCase {

    operator fun invoke(
        password: String,
        validationTypes: List<PasswordValidationType> = PasswordValidationType.values().toList()
    ): Observable<PasswordValidationResult>
}

internal class ValidatePasswordUseCaseImpl(
    private val passwordValidationStrategyProvider: PasswordValidationStrategyProvider
) : ValidatePasswordUseCase {

    override operator fun invoke(
        password: String,
        validationTypes: List<PasswordValidationType>
    ): Observable<PasswordValidationResult> =
        Observable.fromIterable(validationTypes)
            .flatMap { validationType ->
                passwordValidationStrategyProvider
                    .provide(validationType)
                    .validate(password)
                    .toObservable()
            }
}