package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import io.reactivex.rxjava3.core.Single

internal interface PasswordValidationStrategy {

    fun validate(password: String): Single<PasswordValidationResult>
}