package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.COMMON_NAME
import com.mateuszholik.passwordvalidation.usecases.GetIsPasswordANameUseCase
import com.mateuszholik.passwordvalidation.usecases.GetIsPasswordAPetNameUseCase
import com.mateuszholik.passwordvalidation.utils.Numbers
import io.reactivex.rxjava3.core.Single

internal class CommonNameValidationStrategyImpl(
    private val getIsPasswordANameUseCase: GetIsPasswordANameUseCase,
    private val getIsPasswordAPetNameUseCase: GetIsPasswordAPetNameUseCase
) : PasswordValidationStrategy {

    override fun validate(password: String): Single<PasswordValidationResult> =
        Single.zip(
            getIsPasswordANameUseCase(password),
            getIsPasswordAPetNameUseCase(password)
        ) { isName, isPetName ->
            val result = isName && isPetName

            PasswordValidationResult(
                validationType = COMMON_NAME,
                validationResult = result,
                score = if (result) COMMON_NAME.maxScore else Numbers.NONE,
                maxScore = COMMON_NAME.maxScore
            )
        }
}