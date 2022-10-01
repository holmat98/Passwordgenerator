package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.extensions.getTwoDigitYear
import com.mateuszholik.passwordvalidation.extensions.removeNumbers
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import io.reactivex.rxjava3.core.Single
import java.time.LocalDate
import java.util.*

class DateValidationStrategyImpl : PasswordValidationStrategy {

    override fun validate(password: String): Single<PasswordValidationResult> = TODO()
       /* Single.fromCallable {
            val result = areNumbersFromPasswordADate(password.removeNumbers())

            PasswordValidationResult(

            )
        }

    private fun areNumbersFromPasswordADate(numbers: String) =
        when {
            numbers.length < MIN_LENGTH_TO_CREATE_DATE -> false
            numbers.length == MIN_LENGTH_TO_CREATE_DATE ->
                numbers.toInt() >= 90 || numbers.toInt() <= Calendar.getInstance().getTwoDigitYear()
            else -> true
        }

    private companion object {
        const val MIN_LENGTH_TO_CREATE_DATE = 2
    }*/
}